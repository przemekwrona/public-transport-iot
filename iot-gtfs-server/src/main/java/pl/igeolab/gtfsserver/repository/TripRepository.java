package pl.igeolab.gtfsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.igeolab.gtfsserver.entity.Trips;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trips, String> {

    @Query(value = "select trip from Trips trip where trip.route.routeId = :routeId")
    List<Trips> findAllByRouteId(@Param("routeId") String routeId);
}
