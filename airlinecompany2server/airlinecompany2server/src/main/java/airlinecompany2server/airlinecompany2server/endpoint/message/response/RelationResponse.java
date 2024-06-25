package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import airlinecompany2server.airlinecompany2server.model.Flight;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Relation")
public class RelationResponse {

    @XmlElement(name = "DepartureAirport")
    private AirportRelationResponse departureAirport;

    @XmlElement(name = "ArrivalAirport")
    private AirportRelationResponse arrivalAirport;

    public RelationResponse() {
    }

    public RelationResponse(Flight flight) {
        this.departureAirport = new AirportRelationResponse(flight.getDepartureAirport());
        this.arrivalAirport = new AirportRelationResponse(flight.getArrivalAirport());
    }

}
