package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ClassTicketPricing")
public class ClassTicketPricingResponse {
    
    @XmlElement(name = "Price")
    private Float price;

    @XmlElement(name = "Count")
    private Integer count;

    public ClassTicketPricingResponse() {
    }

    public ClassTicketPricingResponse(Float price, Integer count) {
        this.price = price;
        this.count = count;
    }
}
