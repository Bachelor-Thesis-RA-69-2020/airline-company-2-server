package airlinecompany2server.airlinecompany2server.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import airlinecompany2server.airlinecompany2server.model.Airport;

@Repository
public interface AirportRepository extends MongoRepository<Airport, String> {
    Optional<Airport> findByIataCode(String iataCode);
}