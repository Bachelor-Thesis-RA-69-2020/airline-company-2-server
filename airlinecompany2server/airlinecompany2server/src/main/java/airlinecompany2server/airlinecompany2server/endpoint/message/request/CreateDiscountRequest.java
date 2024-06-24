package airlinecompany2server.airlinecompany2server.endpoint.message.request;

import java.time.LocalDateTime;

import airlinecompany2server.airlinecompany2server.model.Discount;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "CreateDiscountRequest")
public class CreateDiscountRequest {
    @XmlElement(name = "FlightCode", required = true)
    private String flightCode;

    @XmlElement(name = "From", required = true)
    private LocalDateTime from;

    @XmlElement(name = "To", required = true)
    private LocalDateTime to;

    @XmlElement(name = "OffValue", required = true)
    private Float offValue;

    public CreateDiscountRequest() {
    }

    public CreateDiscountRequest(String flightCode, LocalDateTime from, LocalDateTime to, Float offValue) {
        this.flightCode = flightCode;
        this.from = from;
        this.to = to;
        this.offValue = offValue;
    }

    public Discount mapToDiscountDomain() {
        return new Discount(from, to, offValue);
    }

    public String getFlightCode() {
        return flightCode;
    }
}
