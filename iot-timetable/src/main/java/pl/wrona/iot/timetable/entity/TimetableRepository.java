package pl.wrona.iot.timetable.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetables, TimetablesId> {

    Timetables findTopByLineAndBrigadeAndDepartureDateIsNotNull(String line, String brigade);

    List<Timetables> findTop10ByLineAndBrigadeAndTimetableDepartureDateGreaterThan(String line, String brigade, LocalDateTime date);


}
