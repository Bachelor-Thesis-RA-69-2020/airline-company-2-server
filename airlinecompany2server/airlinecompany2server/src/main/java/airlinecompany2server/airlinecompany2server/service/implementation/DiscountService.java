package airlinecompany2server.airlinecompany2server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airlinecompany2server.airlinecompany2server.model.Discount;
import airlinecompany2server.airlinecompany2server.model.Flight;
import airlinecompany2server.airlinecompany2server.repository.FlightRepository;
import airlinecompany2server.airlinecompany2server.service.IDiscountService;

@Service
public class DiscountService implements IDiscountService {

    private final FlightRepository flightRepository;

    @Autowired
    public DiscountService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public String create(String flightCode, Discount discount) {
        try {
            Flight flight = flightRepository.findByCode(flightCode).orElseThrow();
            
            discount.validate();

            flight.addDiscount(discount);

            flightRepository.save(flight);

            return "Discount created.";
        } catch(Exception e) {
            return e.getMessage();
        }
    }
    
}
