package pl.wrona.iot.gtfs.collector.feed;

import lombok.AllArgsConstructor;
import pl.wrona.iot.gtfs.collector.properties.FeedProperties;
import pl.wrona.iot.gtfs.collector.properties.FeedType;

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

@AllArgsConstructor
public class UrlFeed implements Runnable {

    private final FeedType feedType = FeedType.URL;

    private final String gtfsUrl;
    private final String directory;
    private final String fileName;

    public UrlFeed(FeedProperties feedProperties) {
        this.gtfsUrl = feedProperties.getUrl();
        this.directory = feedProperties.getDirectory();
        this.fileName = feedProperties.getFileName();
    }

    @Override
    public void run() {
        LocalDate now = LocalDate.now();
        String path = directory.replace("${date}", now.format(DateTimeFormatter.ISO_DATE));
        Path directoryPath = Path.of(path);
        File directoryFile = directoryPath.toFile();

        if (!directoryFile.exists()) {
            directoryFile.mkdir();
        }

        Path filePath = Path.of(path, fileName);

        try (InputStream in = new URL(gtfsUrl).openStream()) {
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
