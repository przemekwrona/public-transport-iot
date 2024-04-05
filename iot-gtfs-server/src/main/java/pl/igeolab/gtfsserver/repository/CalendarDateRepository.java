package pl.igeolab.gtfsserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.igeolab.gtfsserver.entity.CalendarDate;

@Repository
public interface CalendarDateRepository extends JpaRepository<CalendarDate, String> {
}
