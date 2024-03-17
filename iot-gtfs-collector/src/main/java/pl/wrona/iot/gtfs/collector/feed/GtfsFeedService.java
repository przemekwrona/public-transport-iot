package pl.wrona.iot.gtfs.collector.feed;

import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.onebusaway.gtfs.model.Agency;
import org.onebusaway.gtfs.model.ServiceCalendarDate;
import org.onebusaway.gtfs.serialization.GtfsReader;
import org.onebusaway.gtfs.serialization.comparators.ServiceCalendarDateComparator;
import org.springframework.stereotype.Service;
import pl.wrona.iot.gtfs.collector.properties.FeedProperties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GtfsFeedService {

    public GtfsFeed getGtfs(FeedProperties feedProperties) {
        File destinationDirectory = new File(feedProperties.getDirectory());
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        }
        try (InputStream in = new URL(feedProperties.getUrl()).openStream()) {
            Path filePath = Path.of(feedProperties.getDirectory(), "%s.gtfs.zip".formatted(UUID.randomUUID()));
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);

            GtfsReader gtfsReader = new GtfsReader();
            gtfsReader.setInputLocation(filePath.toFile());
            gtfsReader.readEntities(Agency.class);
            gtfsReader.readEntities(ServiceCalendarDate.class);

            Agency agency = gtfsReader.getAgencies().get(0);

            LocalDate minDate = gtfsReader.getEntityStore()
                    .getAllEntitiesForType(ServiceCalendarDate.class).stream()
                    .min(new ServiceCalendarDateComparator())
                    .map(ServiceCalendarDate::getDate)
                    .map(date -> LocalDate.of(date.getYear(), date.getMonth(), date.getDay()))
                    .orElse(null);

            LocalDate maxDate = gtfsReader.getEntityStore()
                    .getAllEntitiesForType(ServiceCalendarDate.class).stream()
                    .max(new ServiceCalendarDateComparator())
                    .map(ServiceCalendarDate::getDate)
                    .map(date -> LocalDate.of(date.getYear(), date.getMonth(), date.getDay()))
                    .orElse(null);

            long days = ChronoUnit.DAYS.between(minDate, maxDate);

            String fileName = feedProperties.getFileName()
                    .replace("${sd}", minDate.format(DateTimeFormatter.ISO_DATE))
                    .replace("${ed}", maxDate.format(DateTimeFormatter.ISO_DATE))
                    .replace("${d}", Long.toString(days));

            filePath.toFile().renameTo(Path.of(feedProperties.getDirectory(), fileName).toFile());

            return new GtfsFeed(feedProperties.getAgency(), agency.getName(), feedProperties.getDirectory(), fileName, minDate, maxDate);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
