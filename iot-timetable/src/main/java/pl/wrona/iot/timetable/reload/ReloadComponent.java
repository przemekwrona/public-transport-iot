package pl.wrona.iot.timetable.reload;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.function.Predicate;

@AllArgsConstructor
public class ReloadComponent implements Runnable {

    private final ReloadService reloadService;
    private final CountDownLatch countDownLatch;

    private final Predicate<String> predicate;

    @Override
    public void run() {
        reloadService.reload(predicate);
        countDownLatch.countDown();
    }
}
