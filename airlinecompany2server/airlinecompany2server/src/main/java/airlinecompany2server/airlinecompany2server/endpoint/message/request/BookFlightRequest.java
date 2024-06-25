package airlinecompany2server.airlinecompany2server.endpoint.message.request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import airlinecompany2server.airlinecompany2server.model.Booking;
import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "BookFlightRequest")
public class BookFlightRequest {

    @XmlElement(name = "FlightCode", required = true)
    protected String flightCode;

    @XmlElement(name = "Class", required = true)
    protected String flightClass;

    @XmlElement(name = "CustomerEmail", required = true)
    protected String customerEmail; 

    @XmlElementWrapper(name = "Passengers")
    @XmlElement(name = "Passenger", required = true)
    protected List<PassengerRequest> passengers;

    public BookFlightRequest(String flightCode, String flightClass, String customerEmail, List<String> fullNames, List<LocalDateTime> birthDates, List<String> passportIds) {
        this.flightCode = flightCode;
        this.flightClass = flightClass;
        this.customerEmail = customerEmail;

        passengers = new ArrayList<>();
        for(int i = 0; i < fullNames.size(); i++) {
            String fullName = fullNames.get(i);
            LocalDateTime birthDate = birthDates.get(i);
            String passportId = passportIds.get(i);

            PassengerRequest passenger = new PassengerRequest(fullName, birthDate, passportId);
            passengers.add(passenger);
        }
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public List<Booking> mapToBookingDomain() {
        List<Booking> bookings = new ArrayList<>();
        for(PassengerRequest passenger : passengers) {
            Booking booking = new Booking(passenger.getFullName(), customerEmail, passenger.getBirthDate(), passenger.getPassportId());
            bookings.add(booking);
        }

        return bookings;
    }
}
