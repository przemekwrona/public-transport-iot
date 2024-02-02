package pl.wrona.iot.gtfs.collector.properties;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wrona.iot.gtfs.collector.feed.UrlFeed;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class PropertyService {

    private final CollectorProperties collectorProperties;

    public void downloadGtfs() {
        collectorProperties.getFeeds().stream()
                .map(PropertyService::getFeed)
//                .map(Thread::new)
                //NOTE GTFS are not downloaded sequentially
                .forEach(Runnable::run);
    }

    private static Runnable getFeed(FeedProperties feedProperties) {
        return switch (feedProperties.getType()) {
            case URL -> new UrlFeed(feedProperties);
        };
    }
}
