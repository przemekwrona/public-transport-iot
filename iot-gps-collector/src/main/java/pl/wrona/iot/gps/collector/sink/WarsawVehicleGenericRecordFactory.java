package pl.wrona.iot.gps.collector.sink;

import lombok.AllArgsConstructor;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import pl.wrona.iot.gps.collector.model.Vehicle;

import java.util.function.Function;

@AllArgsConstructor
public class WarsawVehicleGenericRecordFactory implements Function<Vehicle, GenericData.Record> {
    private final Schema schema;

    @Override
    public GenericData.Record apply(Vehicle warsawVehicle) {
        GenericData.Record record = new GenericData.Record(schema);

        record.put("line", warsawVehicle.getLine());
        record.put("vehicle_type", warsawVehicle.getVehicleType());

        record.put("vehicle_number", warsawVehicle.getVehicleNumber());
        record.put("brigade", warsawVehicle.getBrigade());

        record.put("time", warsawVehicle.getTime());
        record.put("lat", warsawVehicle.getLat());
        record.put("lon", warsawVehicle.getLon());

        return record;
    }
}
