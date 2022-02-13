package pl.wrona.iothermes.client.apollo;

import org.springframework.cloud.openfeign.FeignClient;
import pl.wrona.iot.apollo.api.TimetablesApi;

@FeignClient(name = "${apollo.api.name}", url = "${apollo.api.url}")
public interface ApolloTimetableClient extends TimetablesApi {
}
