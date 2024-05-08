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

//    SELECT * FROM stop_times st
//    JOIN trips t on t.trip_id = st.trip_id
//    JOIN calendar_date cd on cd.service_id = t.service_id
//    JOIN routes r on r.route_id = t.route_id
//    WHERE st.stop_id = '105602'
//    ORDER BY cd.date, st.arrival_time
//    LIMIT 20;

    @Query(value = """
    SELECT stopTimes FROM StopTimes stopTimes
    WHERE stopTimes.stops.stopId = :stopId
    ORDER BY stopTimes.trips.calendarDate.date""")
    List<StopTimes> findStopTimeByStopId(@Param("stopId") String stopId);
}
