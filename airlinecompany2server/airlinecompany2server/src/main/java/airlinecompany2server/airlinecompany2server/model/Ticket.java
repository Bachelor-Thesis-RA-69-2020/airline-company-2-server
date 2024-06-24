package airlinecompany2server.airlinecompany2server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import airlinecompany2server.airlinecompany2server.model.enumeration.FlightClass;

import java.io.Serializable;

@Document
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String code;

    private Float price;

    private FlightClass type;

    private Boolean isBought;

    public Ticket(String code, Float price, FlightClass type) {
        this.code = code;
        this.price = price;
        this.type = type;
        this.isBought = false;
    }
}

