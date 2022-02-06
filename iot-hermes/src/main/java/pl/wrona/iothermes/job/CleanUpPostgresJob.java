package pl.wrona.iothermes.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wrona.iothermes.repository.postgres.VehicleLocationService;

@Slf4j
@Component
@AllArgsConstructor
public class CleanUpPostgresJob {

    public static final int ONE_HOUR = 60 * 60 * 1000;

    private final VehicleLocationService vehicleLocationService;

    @Scheduled(fixedRate = ONE_HOUR)
    public void run() {
        vehicleLocationService.deleteVehicleLocationsByTimeBefore();
    }
}
