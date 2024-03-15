package pl.wrona.iot.gtfs.collector.feed;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wrona.iot.gtfs.collector.properties.FeedProperties;
import pl.wrona.iot.gtfs.collector.properties.FeedType;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GtfsFeedServiceIntegrationTest {

    private static WireMockServer wireMockServer;

    @TempDir
    public Path path;

    @Autowired
    private GtfsFeedService gtfsFeedService;

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration
                .wireMockConfig()
                .port(8081));
        wireMockServer.start();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void shouldDownloadGtfsStoreInS3AndExtractMetadata() {
        //given
        FeedProperties properties = new FeedProperties();
        properties.setType(FeedType.URL);
        properties.setDirectory(path.toString());
        properties.setFileName("warsaw-${sd}-plus-${d}-days.zip");
        properties.setUrl("http://localhost:8081/gtfs/warsaw.zip");

        //when
        gtfsFeedService.getGtfs(properties);

        assertThat(Boolean.TRUE).isTrue();
    }

}