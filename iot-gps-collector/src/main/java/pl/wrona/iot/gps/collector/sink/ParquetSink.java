package pl.wrona.iot.gps.collector.sink;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.io.OutputFile;

import java.io.IOException;

@Slf4j
public class ParquetSink<T> implements Sink<T> {

    private final Schema schema;
    private ParquetWriter<T> writer;

    public ParquetSink(final Schema schema, final OutputFile outputFile) throws IOException {
        this.schema = schema;
        this.writer = AvroParquetWriter.<T>builder(outputFile)
                .withSchema(schema)
                .withDataModel(ReflectData.get())
                .withCompressionCodec(CompressionCodecName.SNAPPY)
                .withWriteMode(ParquetFileWriter.Mode.OVERWRITE)
                .build();
    }

    @Override
    public void save(T element) {
        try {
            writer.write(element);
        } catch (IOException ex) {
            log.error("Can not save record into parquet", ex);
        }

    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
