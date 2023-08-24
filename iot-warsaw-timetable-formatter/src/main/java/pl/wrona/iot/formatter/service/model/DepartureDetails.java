package pl.wrona.iot.formatter.service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartureDetails {

    private String time;
    private String tripId;

    public static DepartureDetails of(String line) {
        String[] departureTokens = line.trim().split("\\s{2,}");
        return DepartureDetails.builder()
                .time(departureTokens[0])
                .tripId(departureTokens[1])
                .build();
    }

}
