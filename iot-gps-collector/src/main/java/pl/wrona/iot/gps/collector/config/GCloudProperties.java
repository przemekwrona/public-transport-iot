package pl.wrona.iot.gps.collector.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "iot.gcloud")
public class GCloudProperties {

    private String projectId;

    private String privateKeyId;
    private String privateKey;
    private String accountId;
    private String accountEmail;

    private List<BucketProperties> bucket;

    public org.apache.hadoop.conf.Configuration getHadoopConfiguration() {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();

        conf.set("fs.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFileSystem");
        conf.set("fs.AbstractFileSystem.gs.impl", "com.google.cloud.hadoop.fs.gcs.GoogleHadoopFS");
        conf.set("fs.gs.create.items.conflict.check.enable", "false");

        conf.set("fs.gs.auth.service.account.private.key.id", privateKeyId);
        conf.set("fs.gs.auth.service.account.private.key", privateKey);
        conf.set("fs.gs.auth.service.account.email", accountEmail);

        return conf;
    }

    public GoogleCredentials getCredentials() throws IOException {
        return ServiceAccountCredentials.fromPkcs8(accountId, accountEmail, privateKey, privateKeyId, List.of());
    }

    public BucketProperties findBucketByName(String bucketName) {
        return this.bucket.stream()
                .filter(b -> b.getBucketName().equals(bucketName))
                .findFirst()
                .orElseThrow();
    }

    public BucketProperties warsawVehicleLiveBucket() {
        return findBucketByName("warsaw_vehicles_live");
    }

    public BucketProperties warsawTimetableBucket() {
        return findBucketByName("warsaw_timetables");
    }
}
