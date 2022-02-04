package pl.wrona.iotapollo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.wrona.iotapollo.client.WarsawApiService;
import pl.wrona.iotapollo.client.WarsawStopService;

@Slf4j
@Component
@Profile("!pi")
@AllArgsConstructor
public class AppRunner implements CommandLineRunner {

    private WarsawStopService warsawStopService;
    private WarsawApiService warsawApiService;

    @Override
    public void run(String... args) throws Exception {

//        log.info("{}", warsawStopService.getClosestStop(52.24f, 21.00f, "9"));
//        log.info("{}", warsawStopService.getClosestStop(52.24f, 21.00f, "9"));
//        log.info("{}", warsawStopService.getClosestStop(52.24f, 21.02f, "9"));
//        log.info("{}", warsawStopService.getClosestStop(52.24f, 21.04f, "9"));
//        log.info("{}", warsawStopService.getClosestStop(52.24f, 21.08f, "9"));

//        log.info("Get Closest Stops");
//        warsawStopService.getClosestStop(52.02f, 21.00f);
//        log.info("Get Closest Stops");
//        warsawStopService.getClosestStop(32.23f, 23.43f);
//        log.info("Get Closest Stops");
//        warsawStopService.getClosestStop(32.23f, 23.43f);
//        log.info("Get Closest Stops");
//        warsawStopService.getClosestStop(32.23f, 23.43f);


        log.info("Do sthd");
        warsawStopService.getLinesOnStop("4121", "03");
        log.info("Do sthd");
        warsawStopService.getLinesOnStop("4121", "03");
        log.info("Do sthd");
        warsawStopService.getLinesOnStop("4121", "03");
//        warsawStopService.getClosestStop(52.55f, 21.05f);
//        warsawStopService.getClosestStop(52.55f, 21.05f);
//        warsawStopService.getClosestStop(52.55f, 21.05f);
    }

}

