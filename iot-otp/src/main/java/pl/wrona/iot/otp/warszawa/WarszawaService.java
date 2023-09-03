package pl.wrona.iot.otp.warszawa;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.otp.api.model.TripPlan;

@Service
@AllArgsConstructor
public class WarszawaService {

    private final WarszawaOtpClient warszawaOtpClient;

    public void getPlan() {
        ResponseEntity<TripPlan> response = warszawaOtpClient.getTripPlan("52.28139182920957,20.934448242187504", "52.18908859025219,20.88432312011719", "4:53pm", "09-03-2023");
        TripPlan plan = response.getBody();
        System.out.println("");
    }
}
