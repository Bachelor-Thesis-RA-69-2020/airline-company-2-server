package airlinecompany2server.airlinecompany2server.service;

import java.util.List;

import airlinecompany2server.airlinecompany2server.model.Booking;

public interface IBookingService {
    String book(String flightCode, String flightClass, String email, List<Booking> bookings);
}