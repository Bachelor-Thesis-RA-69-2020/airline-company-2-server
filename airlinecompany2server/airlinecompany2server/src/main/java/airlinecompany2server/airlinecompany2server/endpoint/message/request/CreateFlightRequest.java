package airlinecompany2server.airlinecompany2server.endpoint.message.request;

import java.time.LocalDateTime;

import airlinecompany2server.airlinecompany2server.model.Flight;
import airlinecompany2server.airlinecompany2server.model.TicketPricing;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "CreateFlightRequest", namespace = "http://localhost:8082/")
public class CreateFlightRequest {

    @XmlElement(name = "TakeOffTime", required = true)
    private LocalDateTime takeoffTime;

    @XmlElement(name = "LandingTime", required = true)
    private LocalDateTime landingTime;

    @XmlElement(name = "LuggageRules", required = true)
    private String luggageRules;

    @XmlElement(name = "JuniorDiscount", required = true)
    private Float juniorDiscount;

    @XmlElement(name = "DepartureAirportIata", required = true)
    private String departureAirportIata;

    @XmlElement(name = "ArrivalAirportIata", required = true)
    private String arrivalAirportIata;

    @XmlElement(name = "EconomyClassPrice", required = true)
    private Float economyClassPrice;

    @XmlElement(name = "EconomyClassTicketCount", required = true)
    private Integer economyClassTicketCount;

    @XmlElement(name = "BusinessClassPrice", required = true)
    private Float businessClassPrice;

    @XmlElement(name = "BusinessClassTicketCount", required = true)
    private Integer businessClassTicketCount;

    @XmlElement(name = "FirstClassPrice", required = true)
    private Float firstClassPrice;

    @XmlElement(name = "FirstClassTicketCount", required = true)
    private Integer firstClassTicketCount;

    public CreateFlightRequest(LocalDateTime takeoffTime, LocalDateTime landingTime,
            String luggageRules, Float juniorDiscount, String departureAirportIata, String arrivalAirportIata,
            Float economyClassPrice, Integer economyClassTicketCount, Float businessClassPrice,
            Integer businessClassTicketCount, Float firstClassPrice, Integer firstClassTicketCount) {
        this.takeoffTime = takeoffTime;
        this.landingTime = landingTime;
        this.luggageRules = luggageRules;
        this.juniorDiscount = juniorDiscount;
        this.departureAirportIata = departureAirportIata;
        this.arrivalAirportIata = arrivalAirportIata;
        this.economyClassPrice = economyClassPrice;
        this.economyClassTicketCount = economyClassTicketCount;
        this.businessClassPrice = businessClassPrice;
        this.businessClassTicketCount = businessClassTicketCount;
        this.firstClassPrice = firstClassPrice;
        this.firstClassTicketCount = firstClassTicketCount;
    }

    public String getDepartureAirportIata() {
        return departureAirportIata;
    }

    public String getArrivalAirportIata() {
        return arrivalAirportIata;
    }

    public Flight mapToFlightDomain() {
        return new Flight(takeoffTime, landingTime, luggageRules, juniorDiscount);
    }

    public TicketPricing mapToTicketPricingDomain() {
        return new TicketPricing(economyClassPrice, economyClassTicketCount, businessClassPrice, businessClassTicketCount, firstClassPrice, firstClassTicketCount);
    } 
}