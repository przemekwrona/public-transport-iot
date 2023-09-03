package pl.wrona.iot.otp.warszawa;

import org.springframework.cloud.openfeign.FeignClient;
import pl.wrona.otp.api.OtpApi;

@FeignClient(value = "warszawa-otp-api", url = "http://192.168.0.32:8080")
public interface WarszawaOtpClient extends OtpApi {
}
