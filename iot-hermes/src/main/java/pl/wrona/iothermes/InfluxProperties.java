package pl.wrona.iothermes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "iot.hermes.influx")
public class InfluxProperties {

    private String url;
    private char[] token;
    private String org;
    private String bucket;

}
