package pl.wrona.iot.gtfs.collector.gtfs;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import pl.wrona.iot.gtfs.collector.api.model.Metadata;
import pl.wrona.iot.gtfs.collector.metadata.GtfsMetadataService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@AllArgsConstructor
public class GtfsDownloadService {

    private final GtfsMetadataService gtfsMetadataService;

    public Resource getGtfs(String agency) throws IOException {
        Metadata metadata = gtfsMetadataService.getLatestGTFS_Metadata(agency);
        Path path = Path.of(metadata.getDirectory(), metadata.getFileName());
        return new ByteArrayResource(Files.readAllBytes(path));
    }
}
