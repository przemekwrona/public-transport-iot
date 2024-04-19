package pl.igeolab.gtfsserver;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.igeolab.gtfsserver.job.GtfsLoaderJob;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
@Component
@AllArgsConstructor
public class CommandLineGtfsRunner implements CommandLineRunner {

    private final GtfsLoaderJob gtfsLoaderJob;

    @Override
    public void run(String... args) throws Exception {
        GtfsServerParams gtfsServerParams = GtfsServerParams.parse(args);

        if (gtfsServerParams.hasPath()) {
            log.info("Run server with GTFS {}", gtfsServerParams.getGtfsPath());
            File file = gtfsServerParams.getGtfsPath().toFile();

            log.info("File {} exists: {} is file {}", gtfsServerParams.getGtfsPath(), gtfsServerParams.getGtfsPath().toFile().exists(), gtfsServerParams.getGtfsPath().toFile().isFile());
            if (file.isDirectory()) {
            }

            Path p = gtfsServerParams.getGtfsPath();
            ZipFile zipFile = new ZipFile(p.toFile());

            zipFile.stream().forEach(entry -> log.info(entry.getName()));
            this.gtfsLoaderJob.loadGtfs(gtfsServerParams.getGtfsPath());
        }
    }
}
