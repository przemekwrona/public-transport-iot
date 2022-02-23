package pl.wrona.iotapollo;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@AllArgsConstructor
@SpringBootApplication
public class IotApolloApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotApolloApplication.class, args);
    }

}

