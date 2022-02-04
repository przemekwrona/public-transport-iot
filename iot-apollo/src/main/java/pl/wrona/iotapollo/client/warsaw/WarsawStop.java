package pl.wrona.iotapollo.client.warsaw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.wrona.warsaw.transport.api.model.WarsawStopValue;

@Data
@Builder
@AllArgsConstructor
public class WarsawStop {

    private String group;
    private String slupek;
    private String name;
    private String streetId;
    private float lon;
    private float lat;
    private String direction;
    private String validateFromDate;

    public double distance(WarsawStop warsawStop) {
        return Math.sqrt(Math.pow(this.lon - warsawStop.getLon(), 2) + Math.pow(this.lat - warsawStop.getLat(), 2));
    }

    public static WarsawStop of(pl.wrona.warsaw.transport.api.model.WarsawStop warsawStop) {
        return WarsawStop.builder()
                .group(findField(warsawStop, "zespol"))
                .slupek(findField(warsawStop, "slupek"))
                .name(findField(warsawStop, "nazwa_zespolu"))
                .streetId(findField(warsawStop, "id_ulicy"))
                .lat(Float.parseFloat(findField(warsawStop, "szer_geo")))
                .lon(Float.parseFloat(findField(warsawStop, "dlug_geo")))
                .direction(findField(warsawStop, "kierunek"))
                .validateFromDate(findField(warsawStop, "obowiazuje_od"))
                .build();
    }

    private static String findField(pl.wrona.warsaw.transport.api.model.WarsawStop warsawStop, String key) {
        return warsawStop.getValues().stream()
                .filter(parameter -> parameter.getKey().equals(key))
                .findFirst()
                .map(WarsawStopValue::getValue)
                .orElse("");
    }

}
