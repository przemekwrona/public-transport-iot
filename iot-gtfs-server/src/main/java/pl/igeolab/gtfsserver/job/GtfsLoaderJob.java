package pl.igeolab.gtfsserver.job;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.springframework.stereotype.Component;
import pl.igeolab.gtfsserver.service.AgencyService;
import pl.igeolab.gtfsserver.service.CalendarDateService;
import pl.igeolab.gtfsserver.service.RoutesService;
import pl.igeolab.gtfsserver.service.StopService;
import pl.igeolab.gtfsserver.service.StopTimeService;
import pl.igeolab.gtfsserver.service.TripService;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Component
@AllArgsConstructor
public class GtfsLoaderJob {

    private final AgencyService agencyService;

    private final CalendarDateService calendarDateService;

    private final DataSource dataSource;

    public void loadGtfs(Path gtfsPath) {

        try {
            GtfsReader gtfsReader = new GtfsReader();
            gtfsReader.setInputLocation(gtfsPath.toFile());

            gtfsReader.readEntities(Agency.class);
            gtfsReader.readEntities(Stop.class);

            agencyService.save(gtfsReader.getAgencies());

            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                for (Stop stop : gtfsReader.getEntityStore().getAllEntitiesForType(Stop.class)) {
                    String query = "insert into stops (stop_id, stop_code, stop_name, stop_desc, stop_lat, stop_lon, zone_id, stop_url, location_type, parent_station) values ('%s', %s, '%s', %s, %f, %f, '%s', '%s', %d, '%s');"
                            .formatted(stop.getId().getId(),
                                    stop.getCode(),
                                    stop.getName().replace("'", "''"),
                                    stop.getDesc(),
                                    stop.getLat(),
                                    stop.getLon(),
                                    stop.getZoneId(),
                                    Optional.of(stop).map(Stop::getUrl).orElse(EMPTY),
                                    stop.getLocationType(),
                                    Optional.of(stop).map(Stop::getParentStation).orElse(EMPTY));
                    statement.addBatch(query);
                }
                statement.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            gtfsReader.readEntities(ServiceCalendarDate.class);
            calendarDateService.save(gtfsReader.getEntityStore().getAllEntitiesForType(ServiceCalendarDate.class));

            gtfsReader.readEntities(Route.class);
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                for (Route route : gtfsReader.getEntityStore().getAllEntitiesForType(Route.class)) {
                    String query = "insert into routes (route_id, agency_id, route_short_name, route_long_name, route_desc, route_type, route_url, route_color, route_text_color) values ('%s', '%s', '%s', '%s', '%s', %d, '%s', '%s', '%s');"
                            .formatted(Optional.of(route).map(Route::getId).map(AgencyAndId::getId).orElseThrow(),
                                    Optional.of(route).map(Route::getId).map(AgencyAndId::getAgencyId).orElseThrow(),
                                    Optional.of(route).map(Route::getShortName).orElse(EMPTY),
                                    Optional.of(route).map(Route::getLongName).orElse(EMPTY),
                                    Optional.of(route).map(Route::getDesc).orElse(EMPTY),
                                    Optional.of(route).map(Route::getType).orElse(null),
                                    Optional.of(route).map(Route::getUrl).orElse(EMPTY),
                                    Optional.of(route).map(Route::getColor).orElse(EMPTY),
                                    Optional.of(route).map(Route::getTextColor).orElse(EMPTY));
                    statement.addBatch(query);
                }
                statement.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            gtfsReader.readEntities(Trip.class);
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                for (Trip trip : gtfsReader.getEntityStore().getAllEntitiesForType(Trip.class)) {
                    String query = "insert into trips (route_id, service_id, trip_id, trip_headsign, trip_short_name, direction_id, block_id, shape_id) values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');"
                            .formatted(Optional.of(trip).map(Trip::getRoute).map(Route::getId).map(AgencyAndId::getId).orElseThrow(),
                                    Optional.of(trip).map(Trip::getServiceId).map(AgencyAndId::getId).orElseThrow(),
                                    Optional.of(trip).map(Trip::getId).map(AgencyAndId::getId).orElseThrow(),
                                    Optional.of(trip).map(Trip::getTripHeadsign).orElse(EMPTY),
                                    Optional.of(trip).map(Trip::getTripShortName).orElse(EMPTY),
                                    Optional.of(trip).map(Trip::getDirectionId).orElse(EMPTY),
                                    Optional.of(trip).map(Trip::getBlockId).orElse(EMPTY),
                                    Optional.of(trip).map(Trip::getShapeId).map(AgencyAndId::getId).orElse(EMPTY));
                    statement.addBatch(query);
                }
                statement.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            gtfsReader.readEntities(StopTime.class);
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                for (StopTime stopTime : gtfsReader.getEntityStore().getAllEntitiesForType(StopTime.class)) {
                    String query = "insert into stop_times (stop_id, trip_id, arrival_time, departure_time, stop_sequence, stop_headsign, pick_up_type, drop_off_type, shape_dist_traveled) VALUES('%s', '%s', %s, %s, %s, %s, %s, %s, %s);"
                            .formatted(stopTime.getStop().getId().getId(),
                                    stopTime.getTrip().getId().getId(),
                                    stopTime.getArrivalTime(),
                                    stopTime.getDepartureTime(),
                                    stopTime.getStopSequence(),
                                    stopTime.getStopHeadsign(),
                                    stopTime.getPickupType(),
                                    stopTime.getDropOffType(),
                                    stopTime.getShapeDistTraveled());
                    statement.addBatch(query);
                }
                statement.executeBatch();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (IOException exception) {

        }
    }
}
