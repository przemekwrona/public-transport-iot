package pl.wrona.iot.timetable.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wrona.iot.timetable.properties.IotPositionsProperties;
import pl.wrona.iot.timetable.sink.ParquetSink;
import pl.wrona.iot.timetable.sink.PathUtils;
import pl.wrona.iot.warsaw.avro.WarsawPositions;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarsawPositionParquetService {

    private final WarsawPositionService warsawPositionService;
    private final IotPositionsProperties iotPositionsProperties;
    private ParquetSink<WarsawPositions> warsawPositionsParquetSink;
    private LocalDateTime lastCreatedSink;
    private ReentrantLock lock = new ReentrantLock();

    public void parquetPositions() throws IOException {
        lock.lock();
        LocalDateTime now = LocalDateTime.now();
        List<WarsawPositions> vehicles = warsawPositionService.getPositions().stream()
                .map(this::buildWarsawPositions)
                .collect(Collectors.toList());

        if (isNull(warsawPositionsParquetSink)) {
            this.warsawPositionsParquetSink = openParquetSink(now);
            this.lastCreatedSink = now;
        }

        if (Math.abs(now.getHour() - lastCreatedSink.getHour()) < iotPositionsProperties.getRefreshFrequency()) {
            warsawPositionsParquetSink.write(vehicles);
        } else {
            warsawPositionsParquetSink.close();
            this.warsawPositionsParquetSink = this.openParquetSink(now);
            this.lastCreatedSink = now;
            this.warsawPositionsParquetSink.write(vehicles);
        }
        lock.unlock();
    }

    private ParquetSink<WarsawPositions> openParquetSink(LocalDateTime now) throws IOException {
        String fileName = PathUtils.getHourPath("{date}.warsaw.gps.parquet", now, 1L);
        String gpsDirectory = String.format("%s/%s/%s", iotPositionsProperties.getDirPath(), now.toLocalDate(), fileName);

        File file = new File(gpsDirectory);
        if (file.exists() && !file.isDirectory()) {
            fileName = PathUtils.getMinutePath("{date}.warsaw.gps.parquet", now);
            gpsDirectory = String.format("%s/%s/%s", iotPositionsProperties.getDirPath(), now.toLocalDate(), fileName);
        }

        return new ParquetSink<>(new Path(gpsDirectory), WarsawPositions.getClassSchema());
    }

    private WarsawPositions buildWarsawPositions(WarsawVehicle v) {
        WarsawPositions position = new WarsawPositions();
        position.setLine(v.getLines());
        position.setLon(v.getLon());
        position.setLat(v.getLat());
        position.setBrigade(v.getBrigade());
        position.setTime(v.getTime());
        return position;
    }

    public void mergeParquet(LocalDate now) {
        java.nio.file.Path directory = java.nio.file.Path.of(iotPositionsProperties.getDirPath(), now.format(DateTimeFormatter.ISO_DATE));
        java.nio.file.Path mergedFilePath = java.nio.file.Path.of(iotPositionsProperties.getDirPath(), now.format(DateTimeFormatter.ISO_DATE), String.format("%s.gps.parquet", now));

        Configuration hdfsConf = defaultConfiguration();

        try (ParquetSink<WarsawPositions> sink = new ParquetSink<>(new Path(mergedFilePath.toUri()), WarsawPositions.getClassSchema())) {

            Files.walk(directory)
                    .filter(path -> !path.endsWith(mergedFilePath.getFileName()))
                    .filter(path -> FilenameUtils.getExtension(path.toString()).equals("parquet"))
                    .forEach(path -> {
                        try (ParquetReader<WarsawPositions> reader = AvroParquetReader
                                .<WarsawPositions>builder(HadoopInputFile.fromPath(new Path(path.toUri()), hdfsConf))
                                .build()) {

                            WarsawPositions record = reader.read();

                            while (nonNull(record)) {
                                sink.write(record);
                                record = reader.read();
                            }
                        } catch (IOException exception) {
                        }
                    });
        } catch (IOException exception) {
        }

        try {
            Files.walk(directory)
                    .filter(path -> !path.equals(mergedFilePath))
                    .map(java.nio.file.Path::toFile)
                    .forEach(File::delete);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static Configuration defaultConfiguration() {
        return new Configuration();
    }

    public void forceClose() throws IOException {
        lock.lock();
        log.info("Close parquet file {}", warsawPositionsParquetSink.getPath());
        warsawPositionsParquetSink.close();
    }

}
