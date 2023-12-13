package pl.wrona.iot.warsaw.timetable.formatter.properties.metro;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.service.CalendarService;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.WarsawTree;

import java.util.List;

@Service
@AllArgsConstructor
public class MetroCalendarService {

    private final MetroProperties metroProperties;
    private final CalendarService calendarService;

    public List<ServiceCalendarDate> getAll(WarsawTree warsawTree) {
        return List.of();
    }

}
