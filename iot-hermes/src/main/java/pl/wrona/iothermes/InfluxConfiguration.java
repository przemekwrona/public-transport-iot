package pl.wrona.iothermes;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.boot.autoconfigure.influx.InfluxDbProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfluxConfiguration {

    @Bean
    public InfluxDB influxDB(final InfluxDbProperties influxDbProperties) {
        return InfluxDBFactory.connect(influxDbProperties.getUrl(), influxDbProperties.getUser(), influxDbProperties.getPassword());
    }

}
