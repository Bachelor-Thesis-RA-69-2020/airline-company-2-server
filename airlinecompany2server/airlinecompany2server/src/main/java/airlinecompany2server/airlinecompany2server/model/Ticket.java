package airlinecompany2server.airlinecompany2server.model;

import org.springframework.data.mongodb.core.mapping.Document;

import airlinecompany2server.airlinecompany2server.model.enumeration.FlightClass;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Document
public class Ticket extends BaseEntity {
    @NotNull(message = "Validation: Ticket code is required")
    @NotEmpty(message = "Validation: Ticket code cannot be empty")
    private String code;

    @NotNull(message = "Validation: Ticket price is required")
    @Min(value = 0, message = "Validation: Ticket price must be greater than or equal to 0")
    private Float price;

    @NotNull(message = "Validation: Ticket type is required")
    private FlightClass type;

    private Boolean isBought;

    public Ticket(String code, Float price, FlightClass type) {
        this.code = code;
        this.price = price;
        this.type = type;
        this.isBought = false;
    }

    public String getCode() {
        return code;
    }

    public Float getPrice() {
        return price;
    }

    public FlightClass getType() {
        return type;
    }

    public Boolean getIsBought() {
        return isBought;
    }
}

