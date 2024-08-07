package airlinecompany2server.airlinecompany2server.endpoint;

import jakarta.xml.bind.JAXBElement;

import java.util.List;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.*;

import airlinecompany2server.airlinecompany2server.endpoint.message.request.FindAllAirportsRequest;
import airlinecompany2server.airlinecompany2server.endpoint.message.response.FindAllAirportsResponse;
import airlinecompany2server.airlinecompany2server.model.Airport;
import airlinecompany2server.airlinecompany2server.service.IAirportService;

@Endpoint
public class FindAllAirportsEndpoint extends BaseEndpoint {

    private static final String NAMESPACE_URI = "http://localhost:8082/";

    @Autowired
    private IAirportService airportService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FindAllAirportsRequest")
    @ResponsePayload
    public JAXBElement<FindAllAirportsResponse> findAllAirports(@RequestPayload JAXBElement<FindAllAirportsRequest> request) {
        List<Airport> airports = airportService.findAll();
        
        FindAllAirportsResponse response = new FindAllAirportsResponse(airports);
        QName qname = new QName("FindAllAirportsResponse"); 
        JAXBElement<FindAllAirportsResponse> jaxbResponse =  new JAXBElement<FindAllAirportsResponse>( qname, FindAllAirportsResponse.class, response); 

        return jaxbResponse;
    }
}

