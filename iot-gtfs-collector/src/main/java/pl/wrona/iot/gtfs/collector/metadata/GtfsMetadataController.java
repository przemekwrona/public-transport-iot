package pl.wrona.iot.gtfs.collector.metadata;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.wrona.iot.gtfs.collector.api.MetadataApi;
import pl.wrona.iot.gtfs.collector.api.model.Metadata;

import java.time.LocalDate;
import java.util.Objects;

@RestController
@AllArgsConstructor
public class GtfsMetadataController implements MetadataApi {

    private final GtfsMetadataService gtfsMetadataService;

    @Override
    public ResponseEntity<Metadata> getMetadata(String agency, LocalDate date) {
        if(Objects.isNull(date)) {
            return ResponseEntity.ok(gtfsMetadataService.getLatestGTFS_Metadata(agency));
        }
        return ResponseEntity.ok(gtfsMetadataService.getLatestGTFS_Metadata(agency, date));
    }
}
