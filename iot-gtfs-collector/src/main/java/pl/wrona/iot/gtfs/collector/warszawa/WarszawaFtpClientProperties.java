package pl.wrona.iot.gtfs.collector.warszawa;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

@Getter
@Setter
@Configuration
@ConfigurationProperties("warszawa.ftp")
public class WarszawaFtpClientProperties {

    private String host;
    private Integer port;
    private String user;
    private String password;

    public String getFtpAddress() {
        return "ftp://%s:%d".formatted(host, port);
    }

    public String getFtpFilePath(String filePath) {
        return "%s/%s".formatted(getFtpAddress(), filePath);
    }

    public URL getFtpFileUrl(String filePath) throws MalformedURLException {
        return new URL("%s/%s".formatted(getFtpAddress(), filePath));
    }
}
