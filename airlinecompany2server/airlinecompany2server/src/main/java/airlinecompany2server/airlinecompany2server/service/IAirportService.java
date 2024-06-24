package airlinecompany2server.airlinecompany2server.service;

import java.util.List;

import airlinecompany2server.airlinecompany2server.model.domain.Airport;

public interface IAirportService {
    List<Airport> findAll();

    Airport findByIataCode(String iataCode);
}