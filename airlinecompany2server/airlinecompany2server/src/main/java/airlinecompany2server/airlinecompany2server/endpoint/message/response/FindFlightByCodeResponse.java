package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import airlinecompany2server.airlinecompany2server.model.Flight;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "FindFlightByCodeResponse")
public class FindFlightByCodeResponse {

    @XmlElement(name = "Flight")
    private FlightResponse flight;

    public FindFlightByCodeResponse() {
    }

    public FindFlightByCodeResponse(Flight flight) {
        this.flight = FlightResponse.mapToResponse(flight);
    }
}
