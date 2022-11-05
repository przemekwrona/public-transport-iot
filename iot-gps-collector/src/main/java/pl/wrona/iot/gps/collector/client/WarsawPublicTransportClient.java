package pl.wrona.iot.gps.collector.client;

import org.springframework.cloud.openfeign.FeignClient;
import pl.wrona.warsaw.transport.api.ApiApi;

@FeignClient(name = "${warsaw.um.api.name}", url = "${warsaw.um.api.url}")
public interface WarsawPublicTransportClient extends ApiApi {
}
