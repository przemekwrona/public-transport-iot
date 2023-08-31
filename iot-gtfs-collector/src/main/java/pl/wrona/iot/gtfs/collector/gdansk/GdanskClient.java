package pl.wrona.iot.gtfs.collector.gdansk;

import org.springframework.cloud.openfeign.FeignClient;
import pl.wrona.gdansk.transport.api.DatasetApi;

@FeignClient(value = "gdsnsk-api", url = "https://ckan.multimediagdansk.pl")
public interface GdanskClient extends DatasetApi {
}
