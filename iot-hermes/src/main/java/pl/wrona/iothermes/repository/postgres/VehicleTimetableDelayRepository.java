package pl.wrona.iothermes.repository.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wrona.iothermes.model.postgres.VehicleTimetableDelay;
import pl.wrona.iothermes.model.postgres.VehicleTimetableDelayId;

import java.time.LocalDateTime;

@Repository
public interface VehicleTimetableDelayRepository extends JpaRepository<VehicleTimetableDelay, VehicleTimetableDelayId> {

    void deleteVehicleTimetableDelaysByTimeBefore(LocalDateTime time);

}
