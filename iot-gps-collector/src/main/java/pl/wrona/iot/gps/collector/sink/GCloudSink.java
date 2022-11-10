package pl.wrona.iot.gps.collector.sink;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.util.HadoopOutputFile;
import pl.wrona.iot.gps.collector.config.GCloudProperties;

import java.io.IOException;


public class GCloudSink<T> implements Sink<T> {

    private final ParquetSink<GenericData.Record> parquetSink;
    private final GenericRecordMapper<T> genericRecordMapper;

    public GCloudSink(GenericRecordMapper<T> mapper, Path path, GCloudProperties gCloudProperties) throws Exception {
        try {
            this.parquetSink = new ParquetSink<>(mapper.getSchema(), HadoopOutputFile.fromPath(path, gCloudProperties.getHadoopConfiguration()));
        } catch (IOException e) {
            throw new Exception("Can not create sink", e);
        }
        this.genericRecordMapper = mapper;
    }

    @Override
    public void save(T element) {
        parquetSink.save(genericRecordMapper.apply(element));
    }

    @Override
    public void close() throws IOException {
        parquetSink.close();
    }
}
