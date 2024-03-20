package pl.wrona.iot.warsaw.timetable.formatter.service;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.onebusaway.gtfs.model.calendar.ServiceDate;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.CalendarLine;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.WarsawTree;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CalendarService {

    public static final String CALENDAR_SECTION = "*KD";
    private Map<String, ServiceCalendarDate> calendarDates;
    public void process(WarsawTree tree) {
        WarsawTree.Node warsawLines = tree.getNode().getNode(CALENDAR_SECTION);

        this.calendarDates = warsawLines.getNodes().stream()
                .map(dateNode -> {
                    LocalDate date = LocalDate.parse(dateNode.getValue().split("\\s{2,}")[0]);
                    ServiceDate serviceDate = new ServiceDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());

                    return dateNode.getNodes().stream()
                            .map(node -> CalendarLine.of(node.getValue()))
                            .map(calendar -> {
                                AgencyAndId serviceId = new AgencyAndId();
                                serviceId.setId(String.format("%s/%s", calendar.getLine(), calendar.getDateType()));

                                ServiceCalendarDate serviceCalendarDate = new ServiceCalendarDate();
                                serviceCalendarDate.setDate(serviceDate);
                                serviceCalendarDate.setServiceId(serviceId);
                                serviceCalendarDate.setExceptionType(1);

                                return serviceCalendarDate;
                            })
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(calendar -> calendar.getServiceId().getId(), Function.identity()));
    }

    public boolean has(String key) {
        return this.calendarDates.containsKey(key);
    }

    public List<ServiceCalendarDate> getAll() {
        return new ArrayList<>(this.calendarDates.values());
    }

}
