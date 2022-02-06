package pl.wrona.iothermes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.wrona.iothermes.client.warsaw.WarsawPublicTransportClient;

@SpringBootTest
class IotHermesApplicationTests {

    @MockBean
    private WarsawPublicTransportClient warsawPublicTransportClient;

    @Test
    void contextLoads() {
    }

}
