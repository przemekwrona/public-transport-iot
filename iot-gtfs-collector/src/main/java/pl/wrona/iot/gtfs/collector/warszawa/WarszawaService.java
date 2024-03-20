package pl.wrona.iot.gtfs.collector.warszawa;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
//import pl.wrona.iot.warsaw.timetable.formatter.GtfsService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class WarszawaService {

    private final WarszawaFtpClient warszawaFtpClient;
//    private final GtfsService gtfsService;

    public void getGtfsForNextNDays(long days) throws IOException {
//        List<File> gtfsFiles = LocalDate.now().datesUntil(LocalDate.now().plusDays(days))
//                .map(this::getTimetableFromFTP).toList();

//        String warsaw3DaysFileName = "%s.3.days.gtfs.zip".formatted(gtfsFiles.get(0).getName().replace(".gtfs.zip", ""));
//        File warsaw3DaysGtfs = new File(warsaw3DaysFileName);

        log.info("Merging Warsaw GTFS");
//        gtfsService.merge(gtfsFiles, warsaw3DaysGtfs);
        log.info("Merging Warsaw GTFS DONE");

    }

//    public File getTimetableFromFTP(LocalDate date) {
//        File gtfs;
//        try {
//            String fileName = TimetableUtils.get7zFileName(date);
//
//            File warsaw7zipTimetable = warszawaFtpClient.getFile(fileName);
//            File warsawTxtTimetable = extract7ZipTimetable(warsaw7zipTimetable);
//            File warsawGtfsDirectory = new File("%s.warszawa.gtfs".formatted(date));
//
////            gtfs = gtfsService.gtfs(warsawTxtTimetable, warsawGtfsDirectory);
//
//            FileUtils.delete(warsaw7zipTimetable);
//            FileUtils.delete(warsawTxtTimetable);
//            FileUtils.deleteDirectory(warsawGtfsDirectory);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return gtfs;
//    }

    private File extract7ZipTimetable(File warsaw7zipTimetable) throws IOException {
        File warsawTxtTimetable = null;

        try (SevenZFile sevenZFile = new SevenZFile(warsaw7zipTimetable)) {

            SevenZArchiveEntry entry = sevenZFile.getNextEntry();
            warsawTxtTimetable = new File(entry.getName());

            byte[] content = new byte[(int) entry.getSize()];
            sevenZFile.read(content);
            Files.write(warsawTxtTimetable.toPath(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return warsawTxtTimetable;
    }

}
