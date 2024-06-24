package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import java.util.List;

import airlinecompany2server.airlinecompany2server.model.Airport;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FindAllAirportsResponse")
public class FindAllAirportsResponse {

    @XmlElementWrapper(name = "Airports")
    @XmlElement(name = "Airport")
    private List<AirportResponse> airports;

    public FindAllAirportsResponse() {
    }

    public FindAllAirportsResponse(List<Airport> airports) {
        this.airports = AirportResponse.mapToResponseList(airports);
    }
}

