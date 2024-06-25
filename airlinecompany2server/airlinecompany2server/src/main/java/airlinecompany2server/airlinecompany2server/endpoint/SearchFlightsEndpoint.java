package airlinecompany2server.airlinecompany2server.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.NodeList;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;

import airlinecompany2server.airlinecompany2server.endpoint.message.request.SearchFlightsRequest;
import airlinecompany2server.airlinecompany2server.endpoint.message.response.SearchFlightsResponse;
import airlinecompany2server.airlinecompany2server.model.Flight;
import airlinecompany2server.airlinecompany2server.service.IFlightService;
import jakarta.xml.bind.JAXBElement;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Endpoint
public class SearchFlightsEndpoint extends BaseEndpoint {

    private static final String NAMESPACE_URI = "http://localhost:8082/";

    @Autowired
    private IFlightService flightService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchFlightsRequest")
    @ResponsePayload
    public JAXBElement<SearchFlightsResponse> searchFlights(@RequestPayload Source request) throws TransformerException {
        SearchFlightsRequest searchRequest = parseRequest(request);
        List<Flight> flights = flightService.search(searchRequest.getFrom(), searchRequest.getTo(), searchRequest.getDepartureAirport(), searchRequest.getArrivalAirport(), searchRequest.getFlightClass(), searchRequest.getPassengerCount());

        SearchFlightsResponse response = new SearchFlightsResponse(flights);
        QName qname = new QName("SearchFlightsResponse");
        JAXBElement<SearchFlightsResponse> jaxbResponse = new JAXBElement<SearchFlightsResponse>(qname, SearchFlightsResponse.class, response);

        return jaxbResponse;
    }

    private SearchFlightsRequest parseRequest(Source source) {
        try {
            String xml = convertToXMLString(source);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            NodeList fromNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "From");
            NodeList toNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "To");
            NodeList departureAirportIATANodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "DepartureAirportIATA");
            NodeList arrivalAirportIATANodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "ArrivalAirportIATA");
            NodeList flightClassNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "FlightClass");
            NodeList passengerCountNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "PassengerCount");

            LocalDateTime from;
            LocalDateTime to;
            String departureAirport;
            String arrivalAirport;
            String flightClass;
            Integer passengerCount;

            if (fromNodeList.getLength() == 0) {
                from = null;
            } else {
                from = LocalDateTime.parse(((Element) fromNodeList.item(0)).getTextContent(), DateTimeFormatter.ISO_DATE_TIME);
            }
            if (toNodeList.getLength() == 0) {
                to = null;
            } else {
                to = LocalDateTime.parse(((Element) toNodeList.item(0)).getTextContent(), DateTimeFormatter.ISO_DATE_TIME);
            }
            if (departureAirportIATANodeList.getLength() == 0) {
                departureAirport = null;
            } else {
                departureAirport = ((Element) departureAirportIATANodeList.item(0)).getTextContent();
            }
            if (arrivalAirportIATANodeList.getLength() == 0) {
                arrivalAirport = null;
            } else {
                arrivalAirport = ((Element) arrivalAirportIATANodeList.item(0)).getTextContent();
            }
            if (flightClassNodeList.getLength() == 0) {
                flightClass = null;
            } else {
                flightClass = ((Element) flightClassNodeList.item(0)).getTextContent();
            }
            if (passengerCountNodeList.getLength() == 0) {
                passengerCount = null;
            } else {
                passengerCount = Integer.parseInt(((Element) passengerCountNodeList.item(0)).getTextContent());
            } 

            SearchFlightsRequest request = new SearchFlightsRequest(from, to, departureAirport, arrivalAirport, flightClass, passengerCount);

            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
