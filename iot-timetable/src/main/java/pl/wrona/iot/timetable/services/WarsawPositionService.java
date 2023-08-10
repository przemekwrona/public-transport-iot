package pl.wrona.iot.timetable.services;

import lombok.RequiredArgsConstructor;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Service;
import pl.wrona.iot.timetable.client.warsaw.WarsawApiService;
import pl.wrona.iot.timetable.properties.IotPositionsProperties;
import pl.wrona.iot.timetable.sink.ParquetSink;
import pl.wrona.iot.timetable.sink.PathUtils;
import pl.wrona.iot.warsaw.avro.WarsawPositions;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class WarsawPositionService {

    private final WarsawApiService warsawApiService;
    private final IotPositionsProperties iotPositionsProperties;
    private ParquetSink<WarsawPositions> warsawPositionsParquetSink;
    private LocalDateTime lastCreatedSink;

    public void parquetPositions() throws IOException {
        LocalDateTime now = LocalDateTime.now();
        List<WarsawPositions> vehicles = warsawApiService.getPositions().stream()
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
    }

    private ParquetSink<WarsawPositions> openParquetSink(LocalDateTime now) throws IOException {
        String fileName = PathUtils.getPath("{date}.warsaw.gps.parquet", now, 1L);
        String gpsDirectory = String.format("%s/%s/%s", iotPositionsProperties.getDirPath(), now, fileName);
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

}
