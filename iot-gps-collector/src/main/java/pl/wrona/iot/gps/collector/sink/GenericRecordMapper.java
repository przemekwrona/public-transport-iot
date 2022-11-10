package pl.wrona.iot.gps.collector.sink;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;

import java.util.function.Function;

public interface GenericRecordMapper<T> extends Function<T, GenericData.Record> {

    Schema getSchema();
}
