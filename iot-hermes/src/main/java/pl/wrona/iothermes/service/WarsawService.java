package pl.wrona.iothermes.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.wrona.iothermes.WarsawUmApiConfiguration;
import pl.wrona.iothermes.client.WarsawPublicTransportService;


@Slf4j
@Component
@AllArgsConstructor
public class WarsawService {

    private final WarsawPublicTransportService warsawPublicTransportService;

    public void getAndSaveVehicles() {
        log.info("Warsaw UM API GET Vehicles");
        warsawPublicTransportService.getBuses();
        warsawPublicTransportService.getTrams();
    }

}
