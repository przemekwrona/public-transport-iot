package pl.wrona.iotapollo;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import pl.wrona.iotapollo.client.WarsawApiService;
import pl.wrona.iotapollo.client.WarsawStopService;

@EnableFeignClients
@AllArgsConstructor
@SpringBootApplication
public class IotApolloApplication {

    private WarsawStopService warsawStopService;

    public static void main(String[] args) {
        SpringApplication.run(IotApolloApplication.class, args);
    }

    @Bean
    public void mmm() {
        warsawStopService.getClosestStop(52.55f, 21.05f);
        warsawStopService.getClosestStop(52.55f, 21.05f);
        warsawStopService.getClosestStop(52.55f, 21.05f);
        warsawStopService.getClosestStop(52.55f, 21.05f);
        warsawStopService.getClosestStop(52.55f, 21.05f);
        warsawStopService.getClosestStop(52.55f, 21.05f);
//        warsawApiService.dodo();
    }

}

