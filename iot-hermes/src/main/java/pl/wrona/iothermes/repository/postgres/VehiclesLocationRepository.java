package pl.wrona.iothermes.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wrona.iothermes.model.postgres.VehicleLocation;

@Repository
public interface VehiclesLocationRepository extends JpaRepository<VehicleLocation, Long> {
}
