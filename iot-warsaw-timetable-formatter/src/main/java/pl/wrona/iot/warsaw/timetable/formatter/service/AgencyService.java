package pl.wrona.iot.warsaw.timetable.formatter.service;

import org.onebusaway.gtfs.model.Agency;
import org.springframework.stereotype.Service;

@Service
public class AgencyService {

    public Agency getAgency() {
        Agency agency = new Agency();
        agency.setId("1");
        agency.setName("Warszawski Transport Publiczny");
        agency.setUrl("https://www.wtp.waw.pl");
        agency.setTimezone("Europe/Warsaw");

        return agency;
    }
}
