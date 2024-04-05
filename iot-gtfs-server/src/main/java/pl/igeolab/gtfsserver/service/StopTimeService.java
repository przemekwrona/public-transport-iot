package pl.igeolab.gtfsserver.service;

import lombok.AllArgsConstructor;
import org.onebusaway.gtfs.model.StopTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.igeolab.gtfsserver.entity.StopTimes;
import pl.igeolab.gtfsserver.repository.StopTimeRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class StopTimeService {

    private final StopTimeRepository stopTimeRepository;
    private final StopService stopService;
    private final TripService tripService;

}
