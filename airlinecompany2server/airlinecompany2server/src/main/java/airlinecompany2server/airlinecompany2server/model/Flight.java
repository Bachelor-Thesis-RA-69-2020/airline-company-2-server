package airlinecompany2server.airlinecompany2server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "flights")
public class Flight extends BaseEntity {
    
    @Id
    private String id;

    @NotNull(message = "Validation: Code is required")
    @NotEmpty(message = "Validation: Code cannot be empty")
    private String code;

    @NotNull(message = "Validation: Takeoff time is required")
    private LocalDateTime takeoffTime;

    @NotNull(message = "Validation: Landing time is required")
    private LocalDateTime landingTime;

    @NotNull(message = "Validation: Duration in minutes is required")
    @Min(value = 1, message = "Validation: Duration must be greater than 0")
    private Integer duration;

    @NotNull(message = "Validation: Luggage rules are required")
    @NotEmpty(message = "Validation: Luggage rules cannot be empty")
    private String luggageRules;

    @NotNull(message = "Validation: Children discount is required")
    @Min(value = 0, message = "Validation: Junior discount must be at least 0")
    @Max(value = 100, message = "Validation: Junior discount must be at most 100")
    private Float juniorDiscount;

    @NotNull(message = "Validation: Departure airport ID is required")
    @DBRef
    private Airport departureAirport;

    @NotNull(message = "Validation: Arrival airport ID is required")
    @DBRef
    private Airport arrivalAirport;

    private List<Ticket> tickets;

    private List<Discount> discounts;

    public Flight() {
    }

    public Flight(LocalDateTime takeoffTime, LocalDateTime landingTime, String luggageRules, Float juniorDiscount) {
        this.takeoffTime = takeoffTime;
        this.landingTime = landingTime;
        Duration durationUnit = Duration.between(takeoffTime, landingTime);
        this.duration = (int) durationUnit.toMinutes();
        this.luggageRules = luggageRules;
        this.juniorDiscount = juniorDiscount;
        tickets = new ArrayList<>();
        discounts= new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTakeoffTime() {
        return takeoffTime;
    }

    public LocalDateTime getLandingTime() {
        return landingTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getLuggageRules() {
        return luggageRules;
    }

    public Float getJuniorDiscount() {
        return juniorDiscount;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setRelation(Airport departureAirport, Airport arrivalAirport) {
        if(departureAirport.getIataCode().equals(arrivalAirport.getIataCode())) {
            throw new IllegalArgumentException("Departure and Arrival airports must be different.");
        }

        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
    }

    @Override
    public void validate() {
        super.validate();

        LocalDateTime now = LocalDateTime.now();

        if (!takeoffTime.isAfter(now) && !landingTime.isAfter(now)) {
            throw new IllegalArgumentException("Validation: Take-off and landing times must be in the future.");
        }

        if (takeoffTime.isAfter(landingTime)) {
            throw new IllegalArgumentException("Validation: Take-off time must be before landing time.");
        }
    }

    public void addTickets(List<Ticket> tickets) {
        this.tickets.addAll(tickets);
    }

    public void addDiscount(Discount discount) {
        this.discounts.add(discount);
    }
}
