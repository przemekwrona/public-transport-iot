package pl.wrona.iothermes.client.warsaw;

import org.springframework.cloud.openfeign.FeignClient;
import pl.wrona.warsaw.transport.api.ApiApi;

@FeignClient(name = "${warsaw.um.api.name}", url = "${warsaw.um.api.url}")
public interface WarsawPublicTransportClient extends ApiApi {
}
