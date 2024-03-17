package pl.wrona.iot.gtfs.collector.metadata;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "GTFS_METADATA")
@NoArgsConstructor
@AllArgsConstructor
class GtfsMetadata {

    @Id
    private String fileName;
    private String directory;

    private String agencyCode;
    private String agencyName;

    private LocalDate endDate;
    private LocalDate startDate;
}
