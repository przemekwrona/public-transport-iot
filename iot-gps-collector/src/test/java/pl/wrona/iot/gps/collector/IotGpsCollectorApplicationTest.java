package pl.wrona.iot.gps.collector;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class IotGpsCollectorApplicationTest {

    @Test
    void shouldBuildContext() {
        assertThat(Boolean.TRUE).isTrue();
    }
}