package pl.wrona.iot.otp;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.wrona.iot.otp.warszawa.WarszawaService;

@Component
@AllArgsConstructor
public class CommandLine implements CommandLineRunner {

    private final WarszawaService warszawaService;

    @Override
    public void run(String... args) throws Exception {
        warszawaService.getPlan();
    }
}
