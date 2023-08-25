package pl.wrona.iot.timetable.client.warsaw;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WarsawStopTest {

    private static Stream<Arguments> provideDistanceToStop() {
        return Stream.of(
                Arguments.of(52.23465f, 21.01522f, 52.23069f, 21.01585f, 442L));
    }

    @ParameterizedTest
    @MethodSource("provideDistanceToStop")
    void shouldCalculateDistanceBetweenStopAndVehicle(Float vehicleLat, Float vehicleLon, Float stopLat, Float stopLon, Long distance) {
        // given
        WarsawStop warsawStop = Mockito.mock(WarsawStop.class);
        when(warsawStop.getLat()).thenReturn(stopLat);
        when(warsawStop.getLon()).thenReturn(stopLon);

        WarsawStop vehicleStop = WarsawStop.builder()
                .lat(vehicleLat)
                .lon(vehicleLon)
                .build();

        // expect
        assertThat(vehicleStop.distance(warsawStop)).isEqualTo(distance);
    }

}