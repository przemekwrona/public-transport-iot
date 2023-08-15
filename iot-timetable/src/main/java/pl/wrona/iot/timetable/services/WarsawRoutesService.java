package pl.wrona.iot.timetable.services;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.springframework.stereotype.Service;
import pl.wrona.iot.timetable.properties.IotTimetablesProperties;
import pl.wrona.iot.timetable.sink.PathUtils;
import pl.wrona.iot.warsaw.avro.WarsawTimetable;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
public class WarsawRoutesService {

    private static final DateTimeFormatter WARSAW_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private final IotTimetablesProperties iotTimetablesProperties;

    public List<WarsawTimetable> load(LocalDate date) {
        List<WarsawTimetable> departures = new ArrayList<>();

        try (ParquetReader<WarsawTimetable> reader = AvroParquetReader
                .<WarsawTimetable>builder(HadoopInputFile.fromPath(new Path(PathUtils.timetablePath(iotTimetablesProperties.getDirPath(), date).toUri()), new Configuration()))
                .build()) {

            WarsawTimetable record = reader.read();

            while (nonNull(record)) {
                departures.add(record);
                record = reader.read();
            }
        } catch (IOException exception) {
        }

        return departures;
    }

    public void getRoutes(LocalDate date) {
        Map<Pair<String, String>, List<WarsawTimetable>> www = load(date).stream()
                .sorted(Comparator.comparing(t -> LocalDateTime.parse(t.getTime(), WARSAW_FORMATTER)))
                .collect(Collectors.groupingBy(timetable -> Pair.of(timetable.getLine().toString(), timetable.getBrigade().toString())));
    }

}
