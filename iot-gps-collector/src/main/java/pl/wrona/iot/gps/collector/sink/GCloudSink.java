package pl.wrona.iot.gps.collector.sink;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import pl.wrona.iot.gps.collector.config.GCloudProperties;
import pl.wrona.iot.gps.collector.model.Vehicle;

import java.io.IOException;


public class GCloudSink implements Sink<Vehicle> {

    private final ParquetSink<GenericData.Record> parquetSink;
    private final WarsawVehicleGenericRecordFactory warsawVehicleGenericRecordFactory;

    public GCloudSink(Schema schema, Path path, GCloudProperties gCloudProperties) throws Exception {
        try {
            this.parquetSink = new ParquetSink<>(schema, HadoopOutputFile.fromPath(path, gCloudProperties.getHadoopConfiguration()));
        } catch (IOException e) {
            throw new Exception("Can not create sink", e);
        }
        this.warsawVehicleGenericRecordFactory = new WarsawVehicleGenericRecordFactory(schema);
    }

    @Override
    public void save(Vehicle element) {
        parquetSink.save(warsawVehicleGenericRecordFactory.apply(element));
    }

    @Override
    public void close() throws IOException {
        parquetSink.close();
    }
}
