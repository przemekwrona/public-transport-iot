package pl.wrona.iot.timetable.client.warsaw;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WarsawStops {

    private List<WarsawStop> stops;

}
