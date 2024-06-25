package airlinecompany2server.airlinecompany2server.service;

import java.time.LocalDateTime;
import java.util.List;

import airlinecompany2server.airlinecompany2server.model.Flight;
import airlinecompany2server.airlinecompany2server.model.TicketPricing;

public interface IFlightService {
    String create(Flight flight, String departureIata, String arrivalIata, TicketPricing pricing);
    Flight findByCode(String code);
    List<Flight> search(LocalDateTime from, LocalDateTime to, String departureAirport, String arrivalAirport, String flightClass, Integer passengerCount);
}
