package airlinecompany2server.airlinecompany2server.service;

import java.util.List;

import airlinecompany2server.airlinecompany2server.model.Airport;

public interface IAirportService {
    List<Airport> findAll();

    List<Airport> search(String searchFilter);
}
