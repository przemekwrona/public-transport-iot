package pl.wrona.iot.gtfs.collector.metadata;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wrona.iot.gtfs.collector.api.model.Metadata;
import pl.wrona.iot.gtfs.collector.feed.GtfsFeed;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class GtfsMetadataService {

    private final GtfsMetadataRepository gtfsMetadataRepository;

    public Metadata getLatestGTFS_Metadata(String cityCode) {
        return gtfsMetadataRepository.findTopByAgencyCodeEqualsOrderByEndDateDesc(cityCode)
                .map(this::buildMetadata)
                .orElse(null);
    }

    private Metadata buildMetadata(GtfsMetadata meta) {
        return new Metadata()
                .agencyCode(meta.getAgencyCode())
                .agencyName(meta.getAgencyName())
                .startDate(meta.getStartDate())
                .endDate(meta.getEndDate());
    }

    public Metadata getLatestGTFS_Metadata(String cityCode, LocalDate date) {
        return gtfsMetadataRepository.findTopByAgencyCodeEqualsAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqualOrderByEndDateDesc(cityCode, date, date)
                .map(this::buildMetadata)
                .orElse(null);
    }

    public void saveMetadata(GtfsFeed gtfsFeed) {
        GtfsMetadata gtfsMetadata = new GtfsMetadata();
        gtfsMetadata.setDirectory(gtfsFeed.directory());
        gtfsMetadata.setFileName(gtfsFeed.fileName());
        gtfsMetadata.setAgencyCode(gtfsFeed.agencyCode());
        gtfsMetadata.setAgencyName(gtfsFeed.agencyName());
        gtfsMetadata.setStartDate(gtfsFeed.startDate());
        gtfsMetadata.setEndDate(gtfsFeed.endDate());

        gtfsMetadataRepository.save(gtfsMetadata);
    }
}
