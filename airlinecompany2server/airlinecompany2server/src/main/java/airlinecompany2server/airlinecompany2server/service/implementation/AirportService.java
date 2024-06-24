package airlinecompany2server.airlinecompany2server.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import airlinecompany2server.airlinecompany2server.model.Airport;
import airlinecompany2server.airlinecompany2server.repository.AirportRepository;
import airlinecompany2server.airlinecompany2server.service.IAirportService;

@Service
public class AirportService implements IAirportService {

    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public List<Airport> findAll() {
        List<Airport> airports = airportRepository.findAll();

        return airports;
    }

    @Override
    public List<Airport> search(String searchFilter) {
        List<Airport> airports = airportRepository.findAll();

        final String filter = searchFilter.toLowerCase();
        airports = airports.stream()
            .filter(a -> a.getAirportName().toLowerCase().contains(filter) || a.getIataCode().toLowerCase().contains(filter))
            .toList();

        return airports;
    }
}
