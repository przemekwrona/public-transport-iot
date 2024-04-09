package pl.wrona.iot.gtfs.collector.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("gtfs.collector")
public class CollectorProperties {

    private FeedProperties warsaw;
    private FeedProperties cyprus;
}
