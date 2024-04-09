package pl.wrona.iot.gtfs.collector.cyprus;

import org.springframework.cloud.openfeign.FeignClient;
import pl.igeolab.gtfs.collector.cyprus.api.RoutesApi;

@FeignClient(value = "cyprus-api", url = "${gtfs.collector.feeds.cyprus.url}")
public interface CyprusClient extends RoutesApi {
}
