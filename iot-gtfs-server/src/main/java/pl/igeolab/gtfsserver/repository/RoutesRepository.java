package pl.igeolab.gtfsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.igeolab.gtfsserver.entity.Routes;

@Repository
public interface RoutesRepository extends JpaRepository<Routes, String> {
}
