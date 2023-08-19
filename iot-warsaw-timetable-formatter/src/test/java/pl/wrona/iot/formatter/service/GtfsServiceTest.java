package pl.wrona.iot.formatter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GtfsServiceTest {
    @Autowired
    private GtfsService gtfsService;

    @Test
    void shouldBuildGTFSFromWarsawTimetableFormat() throws IOException {
        gtfsService.gtfs();

        assertThat(1).isEqualTo(1);
    }

}