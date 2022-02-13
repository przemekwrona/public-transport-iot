package pl.wrona.iotapollo.client.warsaw;


import lombok.Builder;
import lombok.Data;
import pl.wrona.warsaw.transport.api.model.WarsawTimetable;
import pl.wrona.warsaw.transport.api.model.WarsawTimetableValue;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class WarsawDepartures {

    private String brigade;
    private String direction;
    private String route;
    private LocalTime time;


    public static WarsawDepartures of(WarsawTimetable warsawTimetable) {
        return WarsawDepartures.builder()
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

    private static LocalTime getTime(List<WarsawTimetableValue> values, String key) {
        return values.stream()
                .filter(value -> value.getKey().equals(key))
                .findFirst()
                .map(WarsawTimetableValue::getValue)
                .map(LocalTime::parse)
                .orElse(null);
    }

}
