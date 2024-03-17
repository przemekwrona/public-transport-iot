package pl.wrona.iot.gtfs.collector;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.wrona.iot.gtfs.collector.api.model.Metadata;
import pl.wrona.iot.gtfs.collector.feed.GtfsFeedService;
import pl.wrona.iot.gtfs.collector.metadata.GtfsMetadataService;
import pl.wrona.iot.gtfs.collector.properties.CollectorProperties;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Component
@AllArgsConstructor
public class IotGtfsCollectorCommandLine implements CommandLineRunner {

    private final CollectorProperties collectorProperties;
    private final GtfsFeedService gtfsFeedService;
    private final GtfsMetadataService gtfsMetadataService;

    @Override
    public void run(String... args) throws Exception {
        LocalDate now = LocalDate.now();
        collectorProperties.getFeeds().stream()
                .filter(property -> {
                    Metadata metadata = gtfsMetadataService.getLatestGTFS_Metadata(property.getAgency());

                    if (Objects.isNull(metadata)) {
                        return true;
                    }
                    long days = ChronoUnit.DAYS.between(now, metadata.getEndDate());
                    return days <= 7;
                })
                .map(gtfsFeedService::getGtfs)
                .forEach(gtfsMetadataService::saveMetadata);
    }
}
