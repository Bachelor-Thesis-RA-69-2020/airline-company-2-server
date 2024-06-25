package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import java.util.List;
import java.util.stream.Collectors;

import airlinecompany2server.airlinecompany2server.model.Flight;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Flight")
public class FlightResponse {

    @XmlElement(name = "Code")
    private String code;

    @XmlElement(name = "LuggageRules")
    private String luggageRules;

    @XmlElement(name = "Relation")
    private RelationResponse relation;

    @XmlElement(name = "Schedule")
    private FlightScheduleResponse schedule;

    @XmlElement(name = "Pricing")
    private TicketPricingResponse pricing;

    public FlightResponse() {
    }

    public FlightResponse(Flight flight) {
        this.code = flight.getCode();
        this.luggageRules = flight.getLuggageRules();
        this.relation = new RelationResponse(flight); 
        this.schedule = new FlightScheduleResponse(flight);
        this.pricing = new TicketPricingResponse(flight);
    }

    public static final FlightResponse mapToResponse(Flight flight) {
        return new FlightResponse(flight);
    }

    public static final List<FlightResponse> mapToResponseList(List<Flight> flights) {
        return flights.stream()
                       .map(FlightResponse::mapToResponse)
                       .collect(Collectors.toList());
    }
}
