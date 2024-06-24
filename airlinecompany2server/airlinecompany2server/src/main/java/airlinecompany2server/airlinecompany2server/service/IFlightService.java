package airlinecompany2server.airlinecompany2server.service;

import airlinecompany2server.airlinecompany2server.model.Flight;
import airlinecompany2server.airlinecompany2server.model.TicketPricing;

public interface IFlightService {
    String create(Flight flight, String departureIata, String arrivalIata, TicketPricing pricing);
}
