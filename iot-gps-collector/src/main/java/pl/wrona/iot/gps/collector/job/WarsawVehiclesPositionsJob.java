package pl.wrona.iot.gps.collector.job;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gps.collector.client.WarsawPublicTransportService;
import pl.wrona.iot.gps.collector.config.GCloudProperties;
import pl.wrona.iot.gps.collector.parquet.SchemaService;
import pl.wrona.iot.gps.collector.sink.GCloudSink;

@Component
@RequiredArgsConstructor
public class WarsawVehiclesPositionsJob implements Runnable {

    private final WarsawPublicTransportService warsawPublicTransportService;
    private final SchemaService schemaService;
    private final GCloudProperties gCloudProperties;

    private GCloudSink cloudSink;

    @Override
    public void run() {

        try {
            this.cloudSink = new GCloudSink(schemaService.getVehicleLiveSchema(), null, gCloudProperties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        warsawPublicTransportService.getBuses().forEach(cloudSink::save);
        warsawPublicTransportService.getTrams().forEach(cloudSink::save);
    }
}
