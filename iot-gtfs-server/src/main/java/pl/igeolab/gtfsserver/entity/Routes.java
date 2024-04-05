package pl.igeolab.gtfsserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Routes {

    @Id
    private String routeId;

    @ManyToOne
    @JoinColumn(name="agency_id", nullable=false)
    private Agency agency;

    @OneToMany(mappedBy = "route")
    private Set<Trips> trips;

    private String routeShortName;
    private String routeLongName;
    private String routeDesc;
    private int routeType;
    private String routeUrl;
    private String routeColor;
    private String routeTextColor;

}
