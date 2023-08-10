package pl.wrona.iot.timetable.sink;

import org.apache.avro.Schema;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.hadoop.util.HadoopOutputFile;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class ParquetSink<T> implements Closeable {

    private final Path path;
    private final Schema schema;
    private final ParquetWriter<T> writer;

    public ParquetSink(Path path, Schema schema) throws IOException {
        this.path = path;
        this.schema = schema;
        this.writer = AvroParquetWriter.<T>builder(HadoopOutputFile.fromPath(this.path, new Configuration()))
                .withSchema(this.schema)
                .withCompressionCodec(CompressionCodecName.SNAPPY)
                .build();
    }

    public void write(T object) throws IOException {
        writer.write(object);
    }

    public void write(List<T> objects) throws IOException {
        for (T object : objects) {
            write(object);
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
