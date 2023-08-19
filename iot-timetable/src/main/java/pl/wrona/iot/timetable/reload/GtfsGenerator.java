package pl.wrona.iot.timetable.reload;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jooq.lambda.Seq;
import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.model.calendar.ServiceDate;
import org.onebusaway.gtfs.serialization.GtfsWriter;
import pl.wrona.iot.timetable.client.warsaw.WarsawStop;
import pl.wrona.iot.warsaw.avro.WarsawTimetable;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class GtfsGenerator implements Consumer<List<WarsawTimetable>>, Closeable {

    public static final int DAY_IN_SECONDS = 24 * 60 * 60;
    public static final int TEN_MINUTES = 10 * 60;
    public static final int NONE_SPLIT = -1;
    private final GtfsWriter writer;
    private final LocalDate date;

    @Override
    public void accept(List<WarsawTimetable> timetables) {
        Agency agency = getAgency();
        writer.handleEntity(agency);

        AgencyAndId serviceId = new AgencyAndId();
        serviceId.setId("1");

        ServiceDate serviceDate = new ServiceDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());

        ServiceCalendarDate serviceCalendarDate = new ServiceCalendarDate();
        serviceCalendarDate.setDate(serviceDate);
        serviceCalendarDate.setServiceId(serviceId);
        serviceCalendarDate.setExceptionType(1);
        writer.handleEntity(serviceCalendarDate);

        Map<String, Stop> stops = getStops(timetables);
        stops.values().forEach(writer::handleEntity);

        Map<String, Route> routes = getRoutes(timetables);
        routes.values().forEach(writer::handleEntity);

        Map<Triple<String, String, String>, List<WarsawTimetable>> vehiclesAndTheirStops = timetables.stream().sorted(Comparator.comparing(v -> v.getTime().toString())).collect(Collectors.groupingBy(v -> Triple.of(v.getLine().toString(), v.getBrigade().toString(), v.getRoute().toString())));

        vehiclesAndTheirStops.keySet().stream().map(lineAndBrigadeAndRoute -> {
                    List<WarsawTimetable> vehicleStops = vehiclesAndTheirStops.get(lineAndBrigadeAndRoute);

                    List<Integer> splitIndexes = IntStream.range(1, vehicleStops.size()).boxed()
                            .map(i -> {
                                LocalDateTime curr = LocalDateTime.parse(vehicleStops.get(i).getTime().toString());
                                LocalDateTime prev = LocalDateTime.parse(vehicleStops.get(i - 1).getTime().toString());

                                return (Duration.between(prev, curr).getSeconds() < TEN_MINUTES) ? NONE_SPLIT : i;
                            })
                            .filter(i -> i > NONE_SPLIT).collect(Collectors.toList());

                    splitIndexes.add(0);
                    splitIndexes.add(vehicleStops.size() - 1);
                    Collections.sort(splitIndexes);

                    return Seq.of(splitIndexes.toArray(new Integer[0]))
                            .window(0, 1)
                            .filter(w -> w.count() == 2)
                            .map(w -> w.window().toList())
                            .map(p -> vehicleStops.subList(p.get(0), p.get(1) + 1))
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .forEach(trip -> {
                    WarsawTimetable timetable = trip.get(0);
                    Route route = routes.get(timetable.getLine().toString());

                    LocalDateTime startTime = LocalDateTime.parse(trip.get(0).getTime().toString());
                    String tripId = String.format("%s/%s/%s/%02d.%02d", timetable.getLine().toString(), timetable.getBrigade().toString(), timetable.getRoute().toString(), startTime.getHour(), startTime.getMinute());

                    AgencyAndId gtfsTripId = new AgencyAndId();
                    gtfsTripId.setId(tripId);

                    Trip gtfsTrip = new Trip();
                    gtfsTrip.setRoute(route);
                    gtfsTrip.setId(gtfsTripId);
                    gtfsTrip.setServiceId(serviceId);

                    writer.handleEntity(gtfsTrip);

                    int sequence = 0;

                    for (WarsawTimetable avroStop : trip) {
                        sequence++;
                        String stopId = String.format("%s_%s", avroStop.getStopId().toString(), avroStop.getStopNumber().toString());
                        Stop stop = stops.get(stopId);

                        StopTime stopTime = new StopTime();
                        stopTime.setStop(stop);
                        stopTime.setTrip(gtfsTrip);
                        stopTime.setStopSequence(sequence);
                        stopTime.setTimepoint(1);

                        LocalDateTime time = LocalDateTime.parse(avroStop.getTime().toString());
                        stopTime.setArrivalTime(time.toLocalTime().toSecondOfDay());
                        stopTime.setDepartureTime(time.toLocalTime().toSecondOfDay());

                        if (time.toLocalTime().isBefore(LocalTime.of(3, 0))
                                || (time.toLocalTime().isBefore(LocalTime.of(6, 0)) && avroStop.getLine().toString().startsWith("N"))) {
                            stopTime.setArrivalTime(time.toLocalTime().toSecondOfDay() + DAY_IN_SECONDS);
                            stopTime.setDepartureTime(time.toLocalTime().toSecondOfDay() + DAY_IN_SECONDS);
                        }

                        writer.handleEntity(stopTime);
                    }
                });
    }

    private Map<String, Route> getRoutes(List<WarsawTimetable> timetables) {
        Agency agency = getAgency();

        Map<String, Map<String, Long>> linesAndTheirDirection = timetables.stream().collect(Collectors.groupingBy(v -> v.getLine().toString(), Collectors.groupingBy(f -> f.getLineDirection().toString(), Collectors.counting())));

        return linesAndTheirDirection.keySet().stream().sorted().map(line -> {
            List<Pair<String, Long>> directions = linesAndTheirDirection.get(line).keySet().stream().map(key -> Pair.of(key, linesAndTheirDirection.get(line).get(key))).sorted(Comparator.comparing(Pair::getRight)).collect(Collectors.toList());

            AgencyAndId agencyAndId = new AgencyAndId();
            agencyAndId.setId(line);

            String first = directions.get(directions.size() - 2).getKey();
            String last = directions.get(directions.size() - 1).getKey();

            Route route = new Route();
            route.setId(agencyAndId);
            route.setAgency(agency);
            route.setShortName(line);
            route.setLongName(String.format("%s - %s", first, last));

            return route;
        }).collect(Collectors.toMap(r -> r.getId().getId(), Function.identity()));
    }

    private Map<String, Stop> getStops(List<WarsawTimetable> timetables) {
        return timetables.stream()
                .map(WarsawStop::of)
                .collect(Collectors.toSet()).stream()
                .sorted(Comparator.comparing(WarsawStop::getGroup))
                .map(warsawStop -> {
                    AgencyAndId stopId = new AgencyAndId();
                    stopId.setId(String.format("%s_%s", warsawStop.getGroup(), warsawStop.getSlupek()));

                    Stop stop = new Stop();
                    stop.setId(stopId);
                    stop.setDirection(warsawStop.getDirection());
                    stop.setName(String.format("%s %s", warsawStop.getName(), warsawStop.getSlupek()));
                    stop.setLat(warsawStop.getLat());
                    stop.setLon(warsawStop.getLon());
                    return stop;
                })
                .collect(Collectors.toMap(stop -> stop.getId().getId(), Function.identity(), (first, last) -> first));
    }

    private Agency getAgency() {
        Agency agency = new Agency();
        agency.setId("ZTM_WAWA");
        agency.setName("Warszawski Transport Publiczny");
        agency.setUrl("https://www.wtp.waw.pl/");
        agency.setTimezone("Europe/Warsaw");
        return agency;
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }

}
