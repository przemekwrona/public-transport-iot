package pl.wrona.iot.gtfs.collector.cyprus;

import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;

class CyprusApiConfiguration {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
}
