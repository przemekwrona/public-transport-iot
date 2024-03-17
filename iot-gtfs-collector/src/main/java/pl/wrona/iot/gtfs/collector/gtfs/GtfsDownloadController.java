package pl.wrona.iot.gtfs.collector.gtfs;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.wrona.iot.gtfs.collector.api.GtfsApi;

@RestController
public class GtfsDownloadController implements GtfsApi {

    @Override
    public ResponseEntity<Resource> getGtfs(String agency) {
        return null;
    }
}
