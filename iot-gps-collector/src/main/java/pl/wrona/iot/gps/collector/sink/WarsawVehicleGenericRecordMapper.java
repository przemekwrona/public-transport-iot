package pl.wrona.iot.gps.collector.sink;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import pl.wrona.iot.gps.collector.model.Vehicle;

@AllArgsConstructor
public class WarsawVehicleGenericRecordMapper implements GenericRecordMapper<Vehicle> {
    @Getter
    private final Schema schema;

    @Override
    public GenericData.Record apply(Vehicle warsawVehicle) {
        GenericData.Record vehicleRecord = new GenericData.Record(schema);

        vehicleRecord.put("line", warsawVehicle.getLine());
        vehicleRecord.put("vehicle_type", warsawVehicle.getVehicleType());

        vehicleRecord.put("vehicle_number", warsawVehicle.getVehicleNumber());
        vehicleRecord.put("brigade", warsawVehicle.getBrigade());

        vehicleRecord.put("time", warsawVehicle.getTime());
        vehicleRecord.put("lat", warsawVehicle.getLat());
        vehicleRecord.put("lon", warsawVehicle.getLon());

        return vehicleRecord;
    }
}
