package airlinecompany2server.airlinecompany2server.repository;

import airlinecompany2server.airlinecompany2server.model.domain.Airport;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends MongoRepository<Airport, String> {
    Optional<Airport> findByIataCode(String iataCode);
}