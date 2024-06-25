package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import airlinecompany2server.airlinecompany2server.model.Flight;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Discount")
public class DiscountResponse {
    
    @XmlElement(name = "JuniorOffValue")
    private Float juniorOffValue;

    @XmlElement(name = "RegularOffValue")
    private Float regularOffValue;

    public DiscountResponse() {
    }

    public DiscountResponse(Flight flight) {
        this.juniorOffValue = flight.getJuniorDiscount();
        this.regularOffValue = flight.getActiveDiscount();
    }
}
