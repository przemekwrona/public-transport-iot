package pl.wrona.iot.timetable.client.warsaw;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import pl.wrona.iot.timetable.JsonFileUtils;
import pl.wrona.warsaw.transport.api.model.WarsawStops;


import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest
class WarsawStopServiceTest {

    @MockBean
    private WarsawApiClient warsawApiClient;

    @Autowired
    private WarsawStopService warsawStopService;


    @Disabled
    @Test
    void shouldCallWarsawAPIOneTime() {
        // given
        when(warsawApiClient.getStops(any(), any())).thenReturn(ResponseEntity.ok(new pl.wrona.warsaw.transport.api.model.WarsawStops().result(List.of())));

        // when
        IntStream.of(10)
                .forEach(i -> warsawStopService.getWarsawStop("1001", "01"));

        // then
        verify(warsawApiClient, times(1)).getStops(any(), any());
    }

    private static Stream<Arguments> provideStops() {
        return Stream.of(
                Arguments.of(52.214f, 20.980f, "Och-Teatr"),
                Arguments.of(52.220f, 20.985f, "pl.Narutowicza"),
                Arguments.of(52.222f, 20.985f, "Ochota-Ratusz"));
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("provideStops")
    void shouldReturnTheClosestStop(Float lat, Float lon, String name) throws IOException {
        WarsawStops warsawStops = JsonFileUtils.readJson("/warsaw/stops-narutowicza.json", WarsawStops.class);
        when(warsawApiClient.getStops(any(), any())).thenReturn(ResponseEntity.ok(warsawStops));

        // when
        WarsawStop warsawStop = warsawStopService.getStopsInAreaOf35m(lat, lon);

        // then
        assertThat(warsawStop).isNotNull();
        assertThat(warsawStop.getName()).isEqualTo(name);
    }

}
