package pl.wrona.iot.gtfs.collector.gdansk;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wrona.gdansk.transport.api.model.StopTimesSpans;

@Service
@AllArgsConstructor
public class GdanskService {

    private final GdanskClient gdanskClient;

    public StopTimesSpans getLastUpdate() {
        return gdanskClient.getStopTimeSpan().getBody();
    }
}
