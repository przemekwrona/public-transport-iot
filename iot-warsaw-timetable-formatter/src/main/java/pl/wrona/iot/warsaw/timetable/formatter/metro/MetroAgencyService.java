package pl.wrona.iot.warsaw.timetable.formatter.metro;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.Agency;
import org.springframework.stereotype.Service;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroAgency;
import pl.wrona.iot.warsaw.timetable.formatter.properties.metro.MetroProperties;

@Service
@AllArgsConstructor
public class MetroAgencyService {

    private final MetroProperties metroProperties;

    public Agency getAgency() {
        MetroAgency metroAgency = metroProperties.getAgency();

        Agency agency = new Agency();
        agency.setId(metroAgency.getId());
        agency.setName(metroAgency.getName());
        agency.setUrl(metroAgency.getUrl());
        agency.setTimezone(metroAgency.getTimezone());

        return agency;
    }
}
