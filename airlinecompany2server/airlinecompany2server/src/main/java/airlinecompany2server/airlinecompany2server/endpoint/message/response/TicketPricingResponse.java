package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import airlinecompany2server.airlinecompany2server.model.Flight;
import airlinecompany2server.airlinecompany2server.model.enumeration.FlightClass;
import jakarta.xml.bind.annotation.XmlElement;

public class TicketPricingResponse {
    @XmlElement(name = "EconomyClass", nillable = true)
    private ClassTicketPricingResponse economyClassPricing;

    @XmlElement(name = "BusinessClass", nillable = true)
    private ClassTicketPricingResponse businessClassPricing;

    @XmlElement(name = "FirstClass", nillable = true)
    private ClassTicketPricingResponse firstClassPricing;

    @XmlElement(name = "Discount")
    private DiscountResponse discount;

    public TicketPricingResponse() {
    }

    public TicketPricingResponse(Flight flight) {
        float price = flight.getTicketPriceByClass(FlightClass.ECONOMY);
        int count = flight.getTicketCountByClass(FlightClass.ECONOMY);
        if(price == -100 || count == 0) {
            economyClassPricing = null;
        } else {
            economyClassPricing = new ClassTicketPricingResponse(price, count);
        }

        price = flight.getTicketPriceByClass(FlightClass.BUSINESS);
        count = flight.getTicketCountByClass(FlightClass.BUSINESS);
        if(price == -100 || count == 0) {
            businessClassPricing = null;
        } else {
            businessClassPricing = new ClassTicketPricingResponse(price, count);
        }

        price = flight.getTicketPriceByClass(FlightClass.FIRST);
        count = flight.getTicketCountByClass(FlightClass.FIRST);
        if(price == -100 || count == 0) {
            firstClassPricing = null;
        } else {
            firstClassPricing = new ClassTicketPricingResponse(price, count);
        }

        discount = new DiscountResponse(flight);
    }

    
}
