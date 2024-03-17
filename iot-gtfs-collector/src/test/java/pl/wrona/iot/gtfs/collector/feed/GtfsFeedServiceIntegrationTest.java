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
import java.time.LocalDate;

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
        properties.setAgency("WARSAW");
        properties.setDirectory(path.toString());
        properties.setFileName("warsaw-${sd}-plus-${d}-days.gtfs.zip");
        properties.setUrl("http://localhost:8081/gtfs/warsaw.zip");

        //when
        GtfsFeed feed = gtfsFeedService.getGtfs(properties);

        assertThat(feed).isNotNull();
        assertThat(feed.agencyCode()).isEqualTo("WARSAW");
        assertThat(feed.agencyName()).isEqualTo("Warszawski Transport Publiczny");
        assertThat(feed.directory()).isEqualTo(path.toString());
        assertThat(feed.fileName()).isEqualTo("warsaw-2024-03-14-plus-47-days.gtfs.zip");
        assertThat(feed.startDate()).isEqualTo(LocalDate.of(2024,3,14));
        assertThat(feed.endDate()).isEqualTo(LocalDate.of(2024,4,30));
    }

}