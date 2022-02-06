package pl.wrona.iotapollo.client.warsaw;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WarsawStops {

    private List<WarsawStop> stops;

}
