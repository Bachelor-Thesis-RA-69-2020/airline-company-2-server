package airlinecompany2server.airlinecompany2server.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airlinecompany2server.airlinecompany2server.model.Airport;
import airlinecompany2server.airlinecompany2server.model.Flight;
import airlinecompany2server.airlinecompany2server.model.Ticket;
import airlinecompany2server.airlinecompany2server.model.TicketPricing;
import airlinecompany2server.airlinecompany2server.model.enumeration.FlightClass;
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

            String code = generateFlightCode(flight);
            flight.setCode(code);

            List<Ticket> tickets = ticketService.generateTickets(code, pricing);
            flight.addTickets(tickets);

            flight.validate();
            flightRepository.save(flight);

            return "Flight created.";
        } catch(Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public Flight findByCode(String code) {
        Flight flight = flightRepository.findByCode(code).orElseThrow();

        return flight;
    }

    public List<Flight> search(LocalDateTime from, LocalDateTime to, String departureAirport, String arrivalAirport, String flightClass, Integer passengerCount) {
        List<Flight> flights = flightRepository.findAll();

        if (flights.isEmpty()) {
            return List.of();
        }

        flights = searchByDateRange(flights, from, to);
        flights = searchByRelation(flights, departureAirport, arrivalAirport);
        flights = searchByPassengerInformation(flights, flightClass, passengerCount);

        return flights;
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

    private List<Flight> searchByDateRange(List<Flight> flights, LocalDateTime from, LocalDateTime to) {
        if (from != null && to != null && from.isAfter(to)) {
            throw new IllegalArgumentException("Validation: From date must be before To date.");
        }

        flights = searchByFrom(flights, from);
        flights = searchByTo(flights, to);

        return flights;
    }

    private List<Flight> searchByRelation(List<Flight> flights, String departureAirport, String arrivalAirport) {
        if (departureAirport != null && !departureAirport.isEmpty() && arrivalAirport != null && !arrivalAirport.isEmpty() && departureAirport.equals(arrivalAirport)) {
            throw new IllegalArgumentException("Validation: departureAirport and arrivalAirport cannot be the same.");
        }

        if (departureAirport != null && !departureAirport.isEmpty()) {
            airportRepository.findByIataCode(departureAirport).orElseThrow();
        }
        
        if (arrivalAirport != null && !arrivalAirport.isEmpty()) {
            airportRepository.findByIataCode(arrivalAirport).orElseThrow();
        }

        flights = searchByDepartureAirport(flights, departureAirport);
        flights = searchByArrivalAirport(flights, arrivalAirport);
        return flights;
    }

    private List<Flight> searchByPassengerInformation(List<Flight> flights, String flightClass, Integer passengerCount) {
        if (passengerCount == null) {
            passengerCount = 1;
        }

        if (passengerCount < 1) {
            throw new IllegalArgumentException("Validation: Passenger count must be 1 or more.");
        }

        final List<String> validFlightClasses = List.of("Economy", "Business", "First");
        if (flightClass != null && !validFlightClasses.contains(flightClass)) {
            throw new IllegalArgumentException("Validation: Invalid flight class. Must be one of: Economy, Business, First.");
        }

        FlightClass flightClassFilter = null;

        if(flightClass != null && flightClass.equals("Economy")) {
            flightClassFilter = FlightClass.ECONOMY;
        } else if(flightClass != null && flightClass.equals("Business")) {
            flightClassFilter = FlightClass.BUSINESS;
        } else if(flightClass != null && flightClass.equals("First")) {
            flightClassFilter = FlightClass.FIRST;
        }

        final FlightClass filter = flightClassFilter;
        final Integer count = passengerCount;

        flights = flights.stream()
                .filter(flight -> flight.getTicketCountByClass(filter) >= count)
                .collect(Collectors.toList());
        return flights;
    }

    private List<Flight> searchByFrom(List<Flight> flights, LocalDateTime from) {
        if (from != null) {
            flights = flights.stream()
                    .filter(flight -> !flight.getTakeoffTime().isBefore(from))
                    .collect(Collectors.toList());
        }
        return flights;
    }

    private List<Flight> searchByTo(List<Flight> flights, LocalDateTime to) {
        if (to != null) {
            flights = flights.stream()
                    .filter(flight -> !flight.getTakeoffTime().isAfter(to))
                    .collect(Collectors.toList());
        }
        return flights;
    }

    private List<Flight> searchByDepartureAirport(List<Flight> flights, String departureAirport) {
        if (departureAirport != null && !departureAirport.isEmpty()) {
            flights = flights.stream()
                    .filter(flight -> flight.getDepartureAirport().getIataCode().equals(departureAirport))
                    .collect(Collectors.toList());
        }
        return flights;
    }

    private List<Flight> searchByArrivalAirport(List<Flight> flights, String arrivalAirport) {
        if (arrivalAirport != null && !arrivalAirport.isEmpty()) {
            flights = flights.stream()
                    .filter(flight -> flight.getArrivalAirport().getIataCode().equals(arrivalAirport))
                    .collect(Collectors.toList());
        }
        return flights;
    }
}
