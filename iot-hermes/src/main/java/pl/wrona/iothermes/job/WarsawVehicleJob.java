package pl.wrona.iothermes.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wrona.iothermes.service.WarsawService;

@Slf4j
@Component
@AllArgsConstructor
public class WarsawVehicleJob {

    public static final int TEN_SECONDS = 10 * 1000;

    private final WarsawService warsawService;

    @Scheduled(fixedRate = TEN_SECONDS)
    public void run() {
        warsawService.getAndSaveVehicles();
    }

}
