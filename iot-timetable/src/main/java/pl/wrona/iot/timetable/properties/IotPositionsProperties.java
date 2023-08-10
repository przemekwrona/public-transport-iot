package pl.wrona.iot.timetable.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "iot.positions")
public class IotPositionsProperties {

    private String dirPath;
    private Integer refreshFrequency;

}
