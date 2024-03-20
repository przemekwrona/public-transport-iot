package pl.wrona.iot.warsaw.timetable.formatter.service;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.wrona.iot.warsaw.timetable.formatter.GtfsService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GtfsServiceTest {

    @TempDir
    static File dir;
    @Autowired
    private GtfsService gtfsService;

    @BeforeAll
    static void init() throws ZipException {
        String source = "src/test/resources/2023-08-27.warszawa.zip";

        ZipFile zipFile = new ZipFile(source);
        zipFile.extractAll(dir.getPath());
    }

    @Test
    @Disabled
    void shouldBuildGTFSFromWarsawTimetableFormat() throws IOException {
        //given
        File source = new File(String.format("%s/%s", dir.getPath(), "RA230827.TXT"));
        File destination = new File(String.format("%s/%s", dir.getPath(), "warsaw.gtfs"));

        //when
        gtfsService.gtfs(source, destination);

        //then
        assertThat(1).isEqualTo(1);
    }

}