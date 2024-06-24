package airlinecompany2server.airlinecompany2server.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import airlinecompany2server.airlinecompany2server.model.Flight;

@Repository
public interface FlightRepository extends MongoRepository<Flight, String> {
    Optional<Flight> findByCode(String code);
}