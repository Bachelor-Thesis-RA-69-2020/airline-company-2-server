package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import airlinecompany2server.airlinecompany2server.model.Airport;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AirportRelation")
public class AirportRelationResponse {

    @XmlElement(name = "IATACode")
    private String iataCode;

    @XmlElement(name = "Name")
    private String name;

    public AirportRelationResponse() {
    }

    public AirportRelationResponse(Airport airport) {
        this.iataCode = airport.getIataCode();
        this.name = airport.getAirportName();
    }
}
