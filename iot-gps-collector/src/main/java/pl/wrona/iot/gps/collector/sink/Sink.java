package pl.wrona.iot.gps.collector.sink;

import java.io.Closeable;

public interface Sink<T> extends Closeable {

    void save(T element);
}
