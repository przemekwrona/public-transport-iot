package pl.wrona.iot.gtfs.collector.cyprus;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
class CyprusServiceIntegrationTest {

    private static WireMockServer cyprusWireMockServer;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine");

    @Autowired
    private CyprusService cyprusService;

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("gtfs.collector.feeds.cyprus.url", cyprusWireMockServer::baseUrl);
    }

    @BeforeAll
    public static void setUp() {
        cyprusWireMockServer = new WireMockServer(WireMockConfiguration
                .wireMockConfig()
                .port(8010));
        cyprusWireMockServer.start();
    }

    @AfterAll
    public static void tearDown() {
        cyprusWireMockServer.stop();
    }

    @Test
    void shouldGenerateGtfsForCyprusPublicTransport() {
        //when
        cyprusService.generateGtfs();
    }

}