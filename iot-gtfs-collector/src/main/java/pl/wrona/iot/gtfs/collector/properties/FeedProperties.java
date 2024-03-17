package pl.wrona.iot.gtfs.collector.properties;

import lombok.Data;

@Data
public class FeedProperties {

    private FeedType type;
    private String agency;
    private String url;
    private String directory;
    private String fileName;
}
