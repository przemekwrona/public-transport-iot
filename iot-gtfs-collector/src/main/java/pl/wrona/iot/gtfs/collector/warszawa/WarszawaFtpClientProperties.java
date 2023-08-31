package pl.wrona.iot.gtfs.collector.warszawa;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("warszawa.ftp")
public class WarszawaFtpClientProperties {

    private String user;
    private String password;
    private String host;
    private String port;
}
