package pl.wrona.iot.warsaw.timetable.formatter.tree;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import pl.wrona.iot.warsaw.timetable.formatter.tree.model.WarsawTree;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WarsawDeliveredTimetableServiceTest {

    private WarsawDeliveredTimetableService warsawService = new WarsawDeliveredTimetableService();

    @TempDir
    static File dir;

    @BeforeAll
    static void init() throws ZipException {
        String source = "src/test/resources/2023-08-27.warszawa.zip";

        ZipFile zipFile = new ZipFile(source);
        zipFile.extractAll(dir.getPath());
    }

    @Test
    void shouldBuildGTFSFromWarsawTimetableFormat() throws IOException {
        //given
        File gtfs = new File(String.format("%s/%s", dir.getPath(), "RA230827.TXT"));

        //when
        WarsawTree warsawTree = warsawService.load(gtfs);

        //then
        assertNotNull(warsawTree);

        WarsawTree.Node calendarNode = warsawTree.getNode().getNode("*KA");
        assertThat(calendarNode.getValue()).isEqualTo("*KA  699");
    }

}
