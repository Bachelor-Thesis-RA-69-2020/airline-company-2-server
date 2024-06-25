package airlinecompany2server.airlinecompany2server.endpoint.message.request;

import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "SearchFlightsRequest")
public class SearchFlightsRequest {

    @XmlElement(name = "From", required = true)
    protected LocalDateTime from;
    
    @XmlElement(name = "To", required = true)
    protected LocalDateTime to; 

    @XmlElement(name = "DepartureAirportIATA", required = true)
    protected String departureAirport; 

    @XmlElement(name = "ArrivalAirportIATA", required = true)
    protected String arrivalAirport;

    @XmlElement(name = "FlightClass", required = true)
    protected String flightClass; 

    @XmlElement(name = "PassengerCount", required = true)
    protected Integer passengerCount;

    public SearchFlightsRequest(LocalDateTime from, LocalDateTime to, String departureAirport, String arrivalAirport,
            String flightClass, Integer passengerCount) {
        this.from = from;
        this.to = to;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.flightClass = flightClass;
        this.passengerCount = passengerCount;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public Integer getPassengerCount() {
        return passengerCount;
    }

}
