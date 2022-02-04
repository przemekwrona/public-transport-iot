package pl.wrona.iotapollo.client;

import org.springframework.cloud.openfeign.FeignClient;
import pl.wrona.warsaw.transport.api.ApiApi;

@FeignClient(name = "${warsaw.um.api.name}", url = "${warsaw.um.api.url}")
public interface WarsawApiClient extends ApiApi {
}
