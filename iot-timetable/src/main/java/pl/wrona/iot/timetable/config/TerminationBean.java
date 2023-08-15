package pl.wrona.iot.timetable.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.wrona.iot.timetable.services.WarsawPositionParquetService;

import javax.annotation.PreDestroy;

@AllArgsConstructor
public class TerminationBean {

    private final WarsawPositionParquetService warsawPositionParquetService;

    @PreDestroy
    public void onDestroy() throws Exception {
        warsawPositionParquetService.forceClose();
    }
}
