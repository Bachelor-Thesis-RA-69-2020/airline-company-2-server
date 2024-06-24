package airlinecompany2server.airlinecompany2server.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airlinecompany2server.airlinecompany2server.model.Airport;
import airlinecompany2server.airlinecompany2server.model.Flight;
import airlinecompany2server.airlinecompany2server.model.Ticket;
import airlinecompany2server.airlinecompany2server.model.TicketPricing;
import airlinecompany2server.airlinecompany2server.repository.AirportRepository;
import airlinecompany2server.airlinecompany2server.repository.FlightRepository;
import airlinecompany2server.airlinecompany2server.service.IFlightService;
import airlinecompany2server.airlinecompany2server.service.ITicketService;
import airlinecompany2server.airlinecompany2server.utility.Hasher;

@Service
public class FlightService implements IFlightService {

    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    private final ITicketService ticketService;

    @Autowired
    public FlightService(FlightRepository flightRepository, AirportRepository airportRepository, ITicketService ticketService) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
        this.ticketService = ticketService;
    }

    @Override
    public String create(Flight flight, String departureIata, String arrivalIata, TicketPricing pricing) {
        try {
            Airport departureAirport = airportRepository.findByIataCode(departureIata).orElseThrow();
            Airport arrivalAirport = airportRepository.findByIataCode(arrivalIata).orElseThrow();

            flight.setRelation(departureAirport, arrivalAirport);

            flight.validateTimes();

            String code = generateFlightCode(flight);
            flight.setCode(code);

            List<Ticket> tickets = ticketService.generateTickets(code, pricing);
            flight.addTickets(tickets);

            flightRepository.save(flight);

            return "Flight created.";
        } catch(Exception e) {
            return e.getMessage();
        }
    }

    private String generateFlightCode(Flight flight) {
        String id = flight.getTakeoffTime().toString() + 
            flight.getLandingTime().toString() + 
            flight.getDepartureAirport().getIataCode() +
            flight.getArrivalAirport().getIataCode();

        String hash = Hasher.hash(id);

        String code = "AL2-" + hash.substring(0, 10).toUpperCase();

        return code;
    }
}
