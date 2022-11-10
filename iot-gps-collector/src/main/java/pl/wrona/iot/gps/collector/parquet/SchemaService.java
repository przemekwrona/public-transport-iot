package pl.wrona.iot.gps.collector.parquet;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.springframework.stereotype.Service;
import pl.wrona.iot.gps.collector.config.SchemaProperties;

import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class SchemaService {

    private final SchemaProperties schemaProperties;

    public Schema getVehicleLiveSchema() {
        try {
            Schema.Parser parser = new Schema.Parser();
            return parser.parse(schemaProperties.getSchemaContent());
        } catch (IOException ex) {
            log.error(String.format("File %s not found", schemaProperties.getSchemaName()), ex);
        }
        return Schema.create(Schema.Type.RECORD);
    }

    public Schema getTimetableSchema() {
        try {
            Schema.Parser parser = new Schema.Parser();
            return parser.parse(schemaProperties.getSchemaContent());
        } catch (IOException ex) {
            log.error(String.format("File %s not found", schemaProperties.getSchemaName()), ex);
        }
        return Schema.create(Schema.Type.RECORD);
    }
}
