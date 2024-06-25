package airlinecompany2server.airlinecompany2server.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airlinecompany2server.airlinecompany2server.model.Booking;
import airlinecompany2server.airlinecompany2server.model.Flight;
import airlinecompany2server.airlinecompany2server.model.enumeration.FlightClass;
import airlinecompany2server.airlinecompany2server.repository.FlightRepository;
import airlinecompany2server.airlinecompany2server.service.IBookingService;
import airlinecompany2server.airlinecompany2server.utility.EmailSender;
import airlinecompany2server.airlinecompany2server.utility.PdfGenerator;

@Service
public class BookingService implements IBookingService {

    private final FlightRepository flightRepository;
    private final EmailSender emailSender;

    @Autowired
    public BookingService(FlightRepository flightRepository, EmailSender emailSender) {
        this.flightRepository = flightRepository;
        this.emailSender = emailSender;
    }

    @Override
    public String book(String flightCode, String flightClass, String email, List<Booking> bookings) {
        try {
            Flight flight = flightRepository.findByCode(flightCode).orElseThrow();

            FlightClass flightClassFilter = validateClass(flightClass);

            List<String> ticketCodes = new ArrayList<>();
            for(Booking booking : bookings) {
                String ticketCode = bookOne(flight, flightClassFilter, booking);
                ticketCodes.add(ticketCode);
            }

            flightRepository.save(flight);

            PdfGenerator pdfGenerator = new PdfGenerator();
            byte[] pdf = pdfGenerator.generateTicketsPDF(flight, flightClass, bookings, ticketCodes);

            emailSender.sendTicketsEmail(email, pdf);

            return "Booking created.";
        } catch(Exception e) {
            return e.getMessage();
        }
    }

    private String bookOne(Flight flight, FlightClass flightClass, Booking booking) {
        booking.validate();
        
        String ticketCode = flight.buyTicket(flightClass, booking);

        return ticketCode;
    }

    private FlightClass validateClass(String flightClass) {
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

            return flightClassFilter;
    }
    
}
