package pl.igeolab.gtfsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.igeolab.gtfsserver.entity.Agency;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, String> {
}
