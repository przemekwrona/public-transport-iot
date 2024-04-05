package pl.igeolab.gtfsserver.service;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.igeolab.gtfsserver.entity.CalendarDate;
import pl.igeolab.gtfsserver.repository.CalendarDateRepository;

import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CalendarDateService {

    private static final ZoneId WARSAW_ZONE = ZoneId.of("Europe/Warsaw");

    private final CalendarDateRepository calendarDateRepository;

    @Transactional
    public void save(Collection<ServiceCalendarDate> calendarDates) {
        List<CalendarDate> dates = calendarDates.stream()
                .map(calendar -> CalendarDate.builder()
                        .serviceId(calendar.getServiceId().getId())
                        .date(calendar.getDate().getAsDate().toInstant().atZone(WARSAW_ZONE).toLocalDate())
                        .exceptionType(calendar.getExceptionType())
                        .build())
                .toList();

        calendarDateRepository.saveAll(dates);
    }

    public Optional<CalendarDate> findByServiceId(String serviceId) {
        return calendarDateRepository.findById(serviceId);
    }
}
