package pl.wrona.iothermes.client;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.wrona.iothermes.WarsawUmApiConfiguration;
import pl.wrona.warsaw.transport.api.model.WarsawVehicles;

@Slf4j
@Component
@AllArgsConstructor
public class WarsawPublicTransportService {

    private final WarsawUmApiConfiguration warsawUmApiConfiguration;
    private final WarsawPublicTransportClient warsawPublicTransportClient;

    public WarsawVehicles getBuses() {
        return warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getResourceId(), warsawUmApiConfiguration.getApikey(), "1").getBody();
    }

    public WarsawVehicles getTrams() {
        return warsawPublicTransportClient.getVehicles(warsawUmApiConfiguration.getResourceId(), warsawUmApiConfiguration.getApikey(), "2").getBody();
    }

}
