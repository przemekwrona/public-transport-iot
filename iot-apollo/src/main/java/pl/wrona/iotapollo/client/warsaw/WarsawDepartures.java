package pl.wrona.iotapollo.client.warsaw;


import lombok.Builder;
import lombok.Data;
import pl.wrona.warsaw.transport.api.model.WarsawTimetable;
import pl.wrona.warsaw.transport.api.model.WarsawTimetableValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class WarsawDepartures {

    private String line;
    private String brigade;
    private String direction;
    private String route;
    private LocalDateTime time;


    public static WarsawDepartures of(WarsawTimetable warsawTimetable, String line) {
        return WarsawDepartures.builder()
                .line(line)
                .brigade(getValue(warsawTimetable.getValues(), "brygada"))
                .direction(getValue(warsawTimetable.getValues(), "kierunek"))
                .route(getValue(warsawTimetable.getValues(), "trasa"))
                .time(getTime(warsawTimetable.getValues(), "czas"))
                .build();

    }

    private static String getValue(List<WarsawTimetableValue> values, String key) {
        return values.stream().filter(value -> value.getKey().equals(key)).findFirst()
                .map(WarsawTimetableValue::getValue).orElse("");
    }

    private static LocalDateTime getTime(List<WarsawTimetableValue> values, String key) {
        return values.stream()
                .filter(value -> value.getKey().equals(key))
                .findFirst()
                .map(WarsawTimetableValue::getValue)
                .map(time -> {
                    String[] parts = time.split(":");
                    int hour = Integer.parseInt(parts[0]);
                    int minutes = Integer.parseInt(parts[1]);
                    int seconds = Integer.parseInt(parts[2]);

                    if (hour >= 24) {
                        return LocalDateTime.of(LocalDate.now().plusDays(1L), LocalTime.of(hour - 24, minutes, seconds));
                    }

                    return LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minutes, seconds));
                })
                .orElse(null);
    }

}
