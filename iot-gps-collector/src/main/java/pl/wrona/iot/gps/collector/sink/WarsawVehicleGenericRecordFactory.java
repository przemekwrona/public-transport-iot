package pl.wrona.iot.gps.collector.sink;

import lombok.AllArgsConstructor;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import pl.wrona.warsaw.transport.api.model.WarsawVehicle;

import java.util.function.Function;

@AllArgsConstructor
public class WarsawVehicleGenericRecordFactory implements Function<WarsawVehicle, GenericData.Record> {
    private final Schema schema;

    @Override
    public GenericData.Record apply(WarsawVehicle warsawVehicle) {
        GenericData.Record record = new GenericData.Record(schema);

        return record;
    }
}
