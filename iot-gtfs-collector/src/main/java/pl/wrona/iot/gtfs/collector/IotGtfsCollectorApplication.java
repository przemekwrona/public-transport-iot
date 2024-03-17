package pl.wrona.iot.gtfs.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
@ComponentScan(basePackages = {
        "pl.wrona.iot.gtfs.collector",
        "pl.wrona.iot.warsaw.timetable.formatter"
})
public class IotGtfsCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotGtfsCollectorApplication.class, args);
    }

}
