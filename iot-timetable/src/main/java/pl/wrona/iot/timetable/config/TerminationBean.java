package pl.wrona.iot.timetable.config;

import lombok.AllArgsConstructor;
import pl.wrona.iot.timetable.services.WarsawPositionParquetService;

import javax.annotation.PreDestroy;

@AllArgsConstructor
public class TerminationBean {

    private final WarsawPositionParquetService warsawPositionParquetService;

    public void onDestroy() throws Exception {
        warsawPositionParquetService.forceClose();
    }
}
