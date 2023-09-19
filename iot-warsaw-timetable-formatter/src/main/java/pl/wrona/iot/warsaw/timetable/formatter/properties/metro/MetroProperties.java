package pl.wrona.iot.warsaw.timetable.formatter.properties.metro;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "iot.warsaw.metro")
public class MetroProperties {

    private MetroAgency agency;
    private List<MetroRoute> routes;
    private List<MetroTrip> trips;
    private List<MetroStop> stops;
}
