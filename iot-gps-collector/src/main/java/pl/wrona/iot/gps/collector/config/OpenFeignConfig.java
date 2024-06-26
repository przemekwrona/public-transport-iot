package pl.wrona.iot.gps.collector.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("pl.wrona.iot.gps.collector.client")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class OpenFeignConfig {
}
