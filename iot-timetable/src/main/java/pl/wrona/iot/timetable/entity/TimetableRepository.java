package pl.wrona.iot.timetable.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetables, TimetablesId> {

    Timetables findTopByLineAndBrigadeAndDepartureDateIsNotNull(String line, String brigade);

    List<Timetables> findByLineAndBrigadeAndStopIdAndStopNumberAndTimetableDepartureDateBetween(String line, String brigade, String stopId, String stopNumber, LocalDateTime startDate, LocalDateTime endDate);

    List<Timetables> findByLineAndBrigadeAndTimetableDepartureDateBetween(String line, String brigade, LocalDateTime startDate, LocalDateTime endDate);


}
