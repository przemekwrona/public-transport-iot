package pl.wrona.iot.gtfs.collector.warszawa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class WarszawaFtpClient {

    private final WarszawaFtpClientProperties warszawaFtpClientProperties;

    public File getFile(String fileName) throws IOException {
        URLConnection connection = warszawaFtpClientProperties.getFtpFileUrl(fileName).openConnection();

        File warsawTimetables;
        try (InputStream inputStream = connection.getInputStream()) {
            warsawTimetables = new File(fileName);
            Files.copy(inputStream, warsawTimetables.toPath());
        }

        return warsawTimetables;
    }

}
