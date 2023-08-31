package pl.wrona.iot.gtfs.collector.warszawa;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class WarszawaService {

    private final WarszawaFtpClientProperties warszawaFtpClientProperties;

    public void getTimetableFromFTP() throws IOException {
        System.out.println("");
//        String ftpUrl = String.format(
//                "ftp://user:password@localhost:%d/foobar.txt", fakeFtpServer.getServerControlPort());
//
//        URLConnection urlConnection = new URL(ftpUrl).openConnection();
//        InputStream inputStream = urlConnection.getInputStream();
//        Files.copy(inputStream, new File("downloaded_buz.txt").toPath());
//        inputStream.close();
//
//        assertThat(new File("downloaded_buz.txt")).exists();
//
//        new File("downloaded_buz.txt").delete(); // cleanup
    }
}
