package pl.wrona.iot.timetable.client.warsaw;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.lucene.util.SloppyMath;
import pl.wrona.warsaw.transport.api.model.WarsawStopValue;

@Data
@Builder
@AllArgsConstructor
public class WarsawStop {

    private String group;
    private String slupek;
    private String name;
    private String streetId;
    private float lat;
    private float lon;
    private String direction;
    private String validateFromDate;

    public long distance(WarsawStop warsawStop) {
        return distance(warsawStop.getLat(), warsawStop.getLon());
    }

    public long distance(float lat, float lon) {
        return (long) SloppyMath.haversinMeters(this.lat, this.lon, lat, lon);
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
