package pl.wrona.iot.gtfs.collector.metadata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
interface GtfsMetadataRepository extends JpaRepository<GtfsMetadata, String> {

    Optional<GtfsMetadata> findTopByAgencyCodeEqualsOrderByEndDateDesc(String agency);

    Optional<GtfsMetadata> findTopByAgencyCodeEqualsAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqualOrderByEndDateDesc(String agency, LocalDate startDate, LocalDate endDate);

}
