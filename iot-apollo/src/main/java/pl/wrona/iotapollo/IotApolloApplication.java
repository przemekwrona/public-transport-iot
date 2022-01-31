package pl.wrona.iotapollo;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import pl.wrona.iotapollo.client.WarsawApiService;
import pl.wrona.iotapollo.client.WarsawStop;

import java.util.List;

@EnableFeignClients
@SpringBootApplication
@AllArgsConstructor
public class IotApolloApplication {

    private WarsawApiService warsawApiService;

    public static void main(String[] args) {
        SpringApplication.run(IotApolloApplication.class, args);
    }

    @Bean
    public void mmm() {
        List<WarsawStop> stops = warsawApiService.getStops();
        System.out.println();
    }

}
