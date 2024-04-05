package pl.igeolab.gtfsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.igeolab.gtfsserver.entity.Stops;

@Repository
public interface StopRepository extends JpaRepository<Stops, String> {
}
