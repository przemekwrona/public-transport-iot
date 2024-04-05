package pl.igeolab.gtfsserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Builder
@Table(name = "CALENDAR_DATE")
@AllArgsConstructor
@NoArgsConstructor
public class CalendarDate {

    @Id
    private String serviceId;
    private LocalDate date;
    private int exceptionType;

    @OneToMany(mappedBy = "calendarDate")
    private Set<Trips> trips;
}
