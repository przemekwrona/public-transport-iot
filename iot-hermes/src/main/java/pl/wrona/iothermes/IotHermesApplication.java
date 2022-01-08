package pl.wrona.iothermes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class IotHermesApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotHermesApplication.class, args);
    }

}
