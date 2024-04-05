package pl.igeolab.gtfsserver;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Getter
@Setter
public class GtfsServerParams {

    @Parameter(names = "--path",
            description = "GTFS path")
    private String gtfsPath;

    public Path getGtfsPath() {
        return Path.of(gtfsPath);
    }

    public boolean hasPath() {
        return gtfsPath != null;
    }

    public static GtfsServerParams parse(String[] args) {
        GtfsServerParams gtfsServerParams = new GtfsServerParams();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(gtfsServerParams)
                .build();
        jCommander.parse(args);
        return gtfsServerParams;
    }
}
