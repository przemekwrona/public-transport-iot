package pl.wrona.iot.timetable.reload;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.function.Predicate;

@AllArgsConstructor
public class ReloadComponent implements Runnable {

    private final ReloadService reloadService;

    @Override
    public void run() {
        try {
            reloadService.reloadAll();
        } catch (Exception e) {
        }

    }
}
