package pl.igeolab.gtfsserver.service;

import lombok.AllArgsConstructor;
import org.igeolab.iot.gtfs.server.api.model.Agencies;
import org.onebusaway.gtfs.model.Agency;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.igeolab.gtfsserver.repository.AgencyRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class AgencyService {

    private final AgencyRepository agencyRepository;

    @Transactional
    public void save(List<Agency> agencies) {
        agencyRepository.saveAll(agencies.stream()
                .map(agency -> pl.igeolab.gtfsserver.entity.Agency.builder()
                        .agencyId(agency.getId())
                        .agencyName(agency.getName())
                        .agencyUrl(agency.getUrl())
                        .agencyTimezone(agency.getTimezone())
                        .agencyLang(agency.getLang())
                        .agencyPhone(agency.getPhone())
                        .build())
                .toList());
    }

    public pl.igeolab.gtfsserver.entity.Agency findById(String agencyId) {
        return agencyRepository.findById(agencyId).orElse(null);
    }

    public Agencies getAgencies() {
        var agencies = agencyRepository.findAll().stream()
                .map(agency -> new org.igeolab.iot.gtfs.server.api.model.Agency()
                        .agencyId(agency.getAgencyId())
                        .agencyName(agency.getAgencyName()))
                .toList();

        return new Agencies()
                .agencies(agencies);
    }
}
