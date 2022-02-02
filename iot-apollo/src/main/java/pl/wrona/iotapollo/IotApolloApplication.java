package pl.wrona.iotapollo;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@AllArgsConstructor
@SpringBootApplication
public class IotApolloApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotApolloApplication.class, args);
    }

}

