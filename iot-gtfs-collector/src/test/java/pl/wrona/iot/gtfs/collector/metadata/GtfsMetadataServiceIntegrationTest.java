package pl.wrona.iot.gtfs.collector.metadata;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.wrona.iot.gtfs.collector.IotGtfsCollectorCommandLine;
import pl.wrona.iot.gtfs.collector.api.model.Metadata;

import java.time.LocalDate;
import java.util.stream.Stream;

import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@Sql("classpath:sql/gtfs_metadata.sql")
class GtfsMetadataServiceIntegrationTest {

    @MockBean
    private IotGtfsCollectorCommandLine iotGtfsCollectorCommandLine;

    @Container
    static PostgreSQLContainer<?> mysql = new PostgreSQLContainer<>("postgres:14-alpine");

    @Autowired
    private GtfsMetadataService gtfsMetadataService;

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    private static Stream<Arguments> provideMetadataInOutOfScope() {
        return Stream.of(
                Arguments.of(of(2024, 1, 10)),
                Arguments.of(of(2025, 1, 10)));
    }

    @ParameterizedTest
    @MethodSource("provideMetadataInOutOfScope")
    void shouldReturnNullIfDateIsOutOfScope(LocalDate date) {
        //when
        Metadata metadata = gtfsMetadataService.getLatestGTFS_Metadata("WAWA_ZTM", date);

        //then
        assertThat(metadata).isNull();
    }

    private static Stream<Arguments> provideMetadata() {
        return Stream.of(
                Arguments.of(of(2024, 1, 15), of(2024, 1, 15), of(2024, 1, 28)),
                Arguments.of(of(2024, 1, 18), of(2024, 1, 15), of(2024, 1, 28)),
                Arguments.of(of(2024, 1, 20), of(2024, 1, 20), of(2024, 2, 10)),
                Arguments.of(of(2024, 2, 10), of(2024, 2, 10), of(2024, 2, 20))
        );

    }

    @ParameterizedTest
    @MethodSource("provideMetadata")
    void shouldReturnNullIfMetadataDoesNotExist(LocalDate date, LocalDate startDate, LocalDate endDate) {
        //when
        Metadata metadata = gtfsMetadataService.getLatestGTFS_Metadata("WAWA_ZTM", date);

        //then
        assertThat(metadata).isNotNull();
        assertThat(metadata.getStartDate()).isEqualTo(startDate);
        assertThat(metadata.getEndDate()).isEqualTo(endDate);
    }

    @Test
    void shouldReturnLatestMetadata() {
        //when
        Metadata metadata = gtfsMetadataService.getLatestGTFS_Metadata("WAWA_ZTM");

        //then
        assertThat(metadata).isNotNull();
        assertThat(metadata.getStartDate()).isEqualTo(LocalDate.of(2024, 2, 10));
        assertThat(metadata.getEndDate()).isEqualTo(LocalDate.of(2024, 2, 20));
    }

}