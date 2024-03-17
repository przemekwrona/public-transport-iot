package pl.wrona.iot.gtfs.collector.feed;

import java.time.LocalDate;

public record GtfsFeed(String agencyCode, String agencyName, String directory, String fileName, LocalDate startDate,
                       LocalDate endDate) {
}
