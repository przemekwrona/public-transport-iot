package pl.wrona.iot.timetable.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.wrona.iot.timetable.services.WarsawPositionParquetService;

@Configuration
public class ShutdownConfig {

    @Bean
    public TerminationBean getTerminateBean(final WarsawPositionParquetService warsawPositionParquetService) {
        return new TerminationBean(warsawPositionParquetService);
    }
}
