package pl.wrona.iothermes;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxConfiguration {

    @Bean
    public InfluxDBClient influxDBClient(final InfluxProperties influxProperties) {
        return InfluxDBClientFactory.create(influxProperties.getUrl(), influxProperties.getToken(), influxProperties.getOrg(), influxProperties.getBucket());
    }

}
