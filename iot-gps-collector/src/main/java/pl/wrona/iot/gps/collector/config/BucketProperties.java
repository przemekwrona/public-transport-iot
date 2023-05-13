package pl.wrona.iot.gps.collector.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Data
@Component
public class BucketProperties {

    private String bucketName;
    private String schemaPath;
    private int windowSizeInHours;

    public String getSchemaContent() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(ResourceUtils.getFile(schemaPath))) {
            return IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);
        }
    }

    public Schema getSchema() {
        try {
            Schema.Parser parser = new Schema.Parser();
            return parser.parse(getSchemaContent());
        } catch (IOException ex) {
            log.error(String.format("File %s not found", schemaPath), ex);
        }
        return Schema.create(Schema.Type.RECORD);
    }

    public LocalDateTime windowLocalDate(LocalDateTime dateTime) {
        int hour = dateTime.getHour() / windowSizeInHours;
        return LocalDateTime.of(dateTime.toLocalDate(), LocalTime.of(hour * windowSizeInHours, 0));
    }
}
