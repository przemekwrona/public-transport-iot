package pl.wrona.iot.gps.collector.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "warsaw.um.api")
public class WarsawUmApiConfiguration {

    private String resourceId;
    private String apikey;

}
