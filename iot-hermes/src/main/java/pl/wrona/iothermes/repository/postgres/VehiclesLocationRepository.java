package pl.wrona.iothermes.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wrona.iothermes.model.postgres.VehicleLocation;
import pl.wrona.iothermes.model.postgres.VehicleLocationId;

import java.time.LocalDateTime;

@Repository
public interface VehiclesLocationRepository extends JpaRepository<VehicleLocation, VehicleLocationId> {

    void deleteVehicleLocationByTimeBefore(LocalDateTime time);
}
