package pl.wrona.iothermes.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wrona.iothermes.model.postgres.VehicleTimetableDelay;

import java.time.Instant;

@Repository
public interface VehicleTimetableDelayRepository extends JpaRepository<VehicleTimetableDelay, Long> {

    void deleteVehicleLocationsByTimeBefore(Instant instant);

}
