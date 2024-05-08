package pl.igeolab.gtfsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.igeolab.gtfsserver.entity.Stops;

import java.util.List;

@Repository
public interface StopRepository extends JpaRepository<Stops, String> {

    @Query(value = "SELECT s FROM Stops s WHERE s.stopLat - 0.01 < :latitude and :latitude <= s.stopLat + 0.01  and s.stopLon - 0.02 < :longitude and :longitude <= s.stopLon + 0.02")
    List<Stops> findAllByNearest(@Param(value = "latitude") Double latitude, @Param(value = "longitude") Double longitude);

}
