package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import java.util.List;

import airlinecompany2server.airlinecompany2server.model.Flight;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SearchFlightsResponse")
public class SearchFlightsResponse {

    @XmlElementWrapper(name = "Flights")
    @XmlElement(name = "Flight")
    private List<FlightResponse> flights;

    public SearchFlightsResponse() {
    }

    public SearchFlightsResponse(List<Flight> flights) {
        this.flights = FlightResponse.mapToResponseList(flights);
    }
}