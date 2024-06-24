package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import airlinecompany2server.airlinecompany2server.model.domain.Airport;

@XmlRootElement(name = "SearchAirportsResponse")
public class SearchAirportsResponse {

    @XmlElementWrapper(name = "Airports")
    @XmlElement(name = "Airport")
    private List<AirportResponse> airports;

    public SearchAirportsResponse() {
    }

    public SearchAirportsResponse(List<Airport> airports) {
        this.airports = AirportResponse.mapToResponseList(airports);
    }
}
