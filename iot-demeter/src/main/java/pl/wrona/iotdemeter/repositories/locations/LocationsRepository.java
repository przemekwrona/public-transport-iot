package pl.wrona.iotdemeter.repositories.locations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationsRepository extends JpaRepository<VehicleLocation, Long> {

    List<VehicleLocation> findByTimeAfter(LocalDateTime time);

}
