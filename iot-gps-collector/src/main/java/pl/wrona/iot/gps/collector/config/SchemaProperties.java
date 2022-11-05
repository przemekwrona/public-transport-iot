package pl.wrona.iot.gps.collector.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.conscrypt.io.IoUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "iot.parquet")
public class SchemaProperties {

    private String schemaName;

    public File getSchemaFile() throws FileNotFoundException {
        return ResourceUtils.getFile(String.format("classpath:%s", schemaName));
    }

    public String getSchemaContent() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(getSchemaFile())) {
            return IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);
        }
    }
}
