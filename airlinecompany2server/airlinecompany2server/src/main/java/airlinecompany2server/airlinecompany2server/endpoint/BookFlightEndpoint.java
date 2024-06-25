package airlinecompany2server.airlinecompany2server.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.NodeList;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;

import airlinecompany2server.airlinecompany2server.endpoint.message.request.BookFlightRequest;
import airlinecompany2server.airlinecompany2server.endpoint.message.response.BookFlightResponse;
import airlinecompany2server.airlinecompany2server.service.IBookingService;
import jakarta.xml.bind.JAXBElement;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Endpoint
public class BookFlightEndpoint extends BaseEndpoint {
    private static final String NAMESPACE_URI = "http://localhost:8082/";

    @Autowired
    private IBookingService bookingService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "BookFlightRequest")
    @ResponsePayload
    public JAXBElement<BookFlightResponse> bookFlight(@RequestPayload Source request) throws TransformerException {
        BookFlightRequest bookRequest = parseRequest(request);
        String result = bookingService.book(bookRequest.getFlightCode(), bookRequest.getFlightClass(), bookRequest.getCustomerEmail(), bookRequest.mapToBookingDomain());

        BookFlightResponse response = new BookFlightResponse(result);
        QName qname = new QName("BookFlightResponse");
        JAXBElement<BookFlightResponse> jaxbResponse = new JAXBElement<BookFlightResponse>(qname, BookFlightResponse.class, response);

        return jaxbResponse;
    }

    private BookFlightRequest parseRequest(Source source) {
        try {
            String xml = convertToXMLString(source);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            NodeList flightCodeNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "FlightCode");
            NodeList flightClassNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "Class");
            NodeList customerEmailNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "CustomerEmail");
            NodeList fullNameNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "FullName");
            NodeList birthDateNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "BirthDate");
            NodeList passportIdNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "PassportID");

            if (flightCodeNodeList.getLength() == 0) {
                throw new IllegalArgumentException("FlightCode element not found in the SOAP XML.");
            }
            if (flightClassNodeList.getLength() == 0) {
                throw new IllegalArgumentException("Class element not found in the SOAP XML.");
            }
            if (customerEmailNodeList.getLength() == 0) {
                throw new IllegalArgumentException("CustomerEmail element not found in the SOAP XML.");
            }
            if (fullNameNodeList.getLength() == 0) {
                throw new IllegalArgumentException("FullName element not found in the SOAP XML.");
            }
            if (birthDateNodeList.getLength() == 0) {
                throw new IllegalArgumentException("BirthDate element not found in the SOAP XML.");
            }
            if (passportIdNodeList.getLength() == 0) {
                throw new IllegalArgumentException("PassportID element not found in the SOAP XML.");
            }
            
            
            String flighCode = ((Element) flightCodeNodeList.item(0)).getTextContent();
            String flighClass = ((Element) flightClassNodeList.item(0)).getTextContent();
            String customerEmail = ((Element) customerEmailNodeList.item(0)).getTextContent();

            List<String> fullNames = new ArrayList<>();
            for(int i = 0; i < fullNameNodeList.getLength(); i++) {
                String fullName = ((Element) fullNameNodeList.item(i)).getTextContent();
                fullNames.add(fullName);
            }

            List<LocalDateTime> birthDates = new ArrayList<>();
            for(int i = 0; i < birthDateNodeList.getLength(); i++) {
                LocalDateTime birthDate =  LocalDateTime.parse(((Element) birthDateNodeList.item(i)).getTextContent(), DateTimeFormatter.ISO_DATE_TIME);
                birthDates.add(birthDate);
            }

            List<String> passportIds = new ArrayList<>();
            for(int i = 0; i < passportIdNodeList.getLength(); i++) {
                String passportId = ((Element) passportIdNodeList.item(i)).getTextContent();
                passportIds.add(passportId);
            }
            
            BookFlightRequest request = new BookFlightRequest(flighCode, flighClass, customerEmail, fullNames, birthDates, passportIds);
            
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
