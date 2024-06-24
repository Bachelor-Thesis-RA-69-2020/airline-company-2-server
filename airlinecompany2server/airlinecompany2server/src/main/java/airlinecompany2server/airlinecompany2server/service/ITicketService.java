package airlinecompany2server.airlinecompany2server.service;

import java.util.List;

import airlinecompany2server.airlinecompany2server.model.Ticket;
import airlinecompany2server.airlinecompany2server.model.TicketPricing;

public interface ITicketService {
    List<Ticket> generateTickets(String flightCode, TicketPricing pricing);
}
