package pl.igeolab.gtfsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.igeolab.gtfsserver.entity.StopTimes;
import pl.igeolab.gtfsserver.entity.StopTimeId;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StopTimeRepository extends JpaRepository<StopTimes, StopTimeId> {

    @Query(value = "SELECT t FROM StopTimes t WHERE t.stops.stopId = :stopId and t.trips.calendarDate.date >= :date")
    List<StopTimes> findStopTimeByStopId(@Param("stopId") String stopId, @Param("date") LocalDate date);
}
