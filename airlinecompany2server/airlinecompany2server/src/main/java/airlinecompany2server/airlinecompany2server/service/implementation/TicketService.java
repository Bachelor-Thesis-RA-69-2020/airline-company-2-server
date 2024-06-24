package airlinecompany2server.airlinecompany2server.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import airlinecompany2server.airlinecompany2server.model.Ticket;
import airlinecompany2server.airlinecompany2server.model.TicketPricing;
import airlinecompany2server.airlinecompany2server.model.enumeration.FlightClass;
import airlinecompany2server.airlinecompany2server.service.ITicketService;
import airlinecompany2server.airlinecompany2server.utility.Hasher;

@Service
public class TicketService implements ITicketService {
    @Override
    public List<Ticket> generateTickets(String flightCode, TicketPricing pricing) {
        List<Ticket> economyClassTickets = generateClassTickets(flightCode, FlightClass.ECONOMY, pricing.getEconomyClassPrice(), pricing.getEconomyClassTicketCount());
        List<Ticket> businessClassTickets = generateClassTickets(flightCode, FlightClass.BUSINESS, pricing.getBusinessClassPrice(), pricing.getBusinessClassTicketCount());
        List<Ticket> firstClassTickets = generateClassTickets(flightCode, FlightClass.FIRST, pricing.getFirstClassPrice(), pricing.getFirstClassTicketCount());
        
        List<Ticket> allTickets = new ArrayList<>();
        allTickets.addAll(economyClassTickets);
        allTickets.addAll(businessClassTickets);
        allTickets.addAll(firstClassTickets);

        return allTickets;
    }

    private List<Ticket> generateClassTickets(String flightCode, FlightClass flightClass, Float price, Integer count) {
        List<Ticket> tickets = new ArrayList<>();

        for(int i = 1; i <= count; i++) {
            String code = generateTicketCode(flightCode, flightClass, i);
            Ticket ticket = new Ticket(code, price, flightClass);
            ticket.validate();
            tickets.add(ticket);
        }

        return tickets;
    }

    private String generateTicketCode(String flightCode, FlightClass flightClass, int serialNumber) {
        String id = flightCode + 
            flightClass.toString() + 
            serialNumber;

        String hash = Hasher.hash(id);

        String flightClassLabel = "";

        if(flightClass.equals(FlightClass.ECONOMY)) {
            flightClassLabel = "Economy-";
        } else if(flightClass.equals(FlightClass.BUSINESS)) {
            flightClassLabel = "Business-";
        } else if(flightClass.equals(FlightClass.FIRST)) {
            flightClassLabel = "First-";
        }

        String code = "AL2-" + flightClassLabel + hash.substring(0, 10).toUpperCase();

        return code;
    }
}
