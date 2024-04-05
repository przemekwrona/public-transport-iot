package pl.igeolab.gtfsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class IotGtfsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotGtfsServerApplication.class, args);
    }

}
