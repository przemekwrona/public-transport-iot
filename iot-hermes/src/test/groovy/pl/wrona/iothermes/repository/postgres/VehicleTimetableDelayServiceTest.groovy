package pl.wrona.iothermes.repository.postgres

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import pl.wrona.iothermes.client.apollo.ApolloTimetableClient
import pl.wrona.iothermes.model.CityCode
import pl.wrona.iothermes.model.VehicleLocation
import pl.wrona.iothermes.model.VehicleType
import pl.wrona.iothermes.model.postgres.VehicleTimetableDelay
import spock.lang.Specification

import java.time.Instant

@SpringBootTest
class VehicleTimetableDelayServiceTest extends Specification {

  @MockBean
  private ApolloTimetableClient apolloTimetableClient;

  @Autowired
  private VehicleTimetableDelayRepository vehicleDelayRepository;

  @Autowired
  private VehicleTimetableDelayService vehicleDelayService;

  def shouldSaveVehiclesWithDelay() {
    given:
    VehicleLocation vehicleLocation = VehicleLocation.builder()
            .cityCode(CITY_CODE)
            .vehicleType(VEHICLE_TYPE)
            .line(LINE)
            .lat(LAT)
            .lon(LON)
            .vehicleNumber(VEHICLE_NUMBER)
            .brigade(BRIGADE)
            .time(TIME)
            .build()
    List<VehicleLocation> vehicleLocations = List.of(vehicleLocation)

    when:
    vehicleDelayService.updateVehiclesWithDelay(vehicleLocations)

    then:
    List<VehicleTimetableDelay> savedVehicleLocations = vehicleDelayRepository.findAll()
    savedVehicleLocations.size() == 1
    savedVehicleLocations.first().getCityCode() == CITY_CODE
    savedVehicleLocations.first().getVehicleType() == VEHICLE_TYPE
    savedVehicleLocations.first().getLine() == LINE
    savedVehicleLocations.first().getLat() == LAT
    savedVehicleLocations.first().getLon() == LON
    savedVehicleLocations.first().getLon() == LON
    savedVehicleLocations.first().getVehicleNumber() == VEHICLE_NUMBER
    savedVehicleLocations.first().getBrigade() == BRIGADE
    savedVehicleLocations.first().getTime() == TIME

    where:
    CITY_CODE     | VEHICLE_TYPE    | LINE  | LAT     | LON     | VEHICLE_NUMBER | BRIGADE | TIME
    CityCode.WAWA | VehicleType.BUS | "182" | 52.000f | 21.000f | "6020"         | "2"     | Instant.now()
  }
}
