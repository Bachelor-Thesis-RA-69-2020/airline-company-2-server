package airlinecompany2server.airlinecompany2server.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.NodeList;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;

import airlinecompany2server.airlinecompany2server.endpoint.message.request.CreateFlightRequest;
import airlinecompany2server.airlinecompany2server.endpoint.message.response.CreateFlightResponse;
import airlinecompany2server.airlinecompany2server.service.IFlightService;
import jakarta.xml.bind.JAXBElement;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Endpoint
public class CreateFlightEndpoint extends BaseEndpoint {
    private static final String NAMESPACE_URI = "http://localhost:8082/";

    @Autowired
    private IFlightService flightService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateFlightRequest")
    @ResponsePayload
    public JAXBElement<CreateFlightResponse> createFlight(@RequestPayload Source request) throws TransformerException {
        CreateFlightRequest createRequest = parseRequest(request);
        String result = flightService.create(createRequest.mapToFlightDomain(), createRequest.getDepartureAirportIata(), createRequest.getArrivalAirportIata(), createRequest.mapToTicketPricingDomain());

        CreateFlightResponse response = new CreateFlightResponse(result);
        QName qname = new QName("CreateFlightResponse");
        JAXBElement<CreateFlightResponse> jaxbResponse = new JAXBElement<CreateFlightResponse>(qname, CreateFlightResponse.class, response);

        return jaxbResponse;
    }

    private CreateFlightRequest parseRequest(Source source) {
        try {
            String xml = convertToXMLString(source);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            NodeList takeoffTimeNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "TakeOffTime");
            NodeList landingTimeNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "LandingTime");
            NodeList luggageRulesNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "LuggageRules");
            NodeList juniorDiscountNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "JuniorDiscount");
            NodeList departureAirportIataNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "DepartureAirportIata");
            NodeList arrivalAirportIataNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "ArrivalAirportIata");
            NodeList economyClassPriceNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "EconomyClassPrice");
            NodeList economyClassTicketCountNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "EconomyClassTicketCount");
            NodeList businessClassPriceNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "BusinessClassPrice");
            NodeList businessClassTicketCountNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "BusinessClassTicketCount");
            NodeList firstClassPriceNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "FirstClassPrice");
            NodeList firstClassTicketCountNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "FirstClassTicketCount");

            if (takeoffTimeNodeList.getLength() == 0) {
                throw new IllegalArgumentException("TakeOffTime element not found in the SOAP XML.");
            }
            if (landingTimeNodeList.getLength() == 0) {
                throw new IllegalArgumentException("LandingTime element not found in the SOAP XML.");
            }
            if (luggageRulesNodeList.getLength() == 0) {
                throw new IllegalArgumentException("LuggageRules element not found in the SOAP XML.");
            }
            if (juniorDiscountNodeList.getLength() == 0) {
                throw new IllegalArgumentException("JuniorDiscount element not found in the SOAP XML.");
            }
            if (departureAirportIataNodeList.getLength() == 0) {
                throw new IllegalArgumentException("DepartureAirportIata element not found in the SOAP XML.");
            }
            if (arrivalAirportIataNodeList.getLength() == 0) {
                throw new IllegalArgumentException("ArrivalAirportIata element not found in the SOAP XML.");
            }
            if (economyClassPriceNodeList.getLength() == 0) {
                throw new IllegalArgumentException("EconomyClassPrice element not found in the SOAP XML.");
            }
            if (economyClassTicketCountNodeList.getLength() == 0) {
                throw new IllegalArgumentException("EconomyClassTicketCount element not found in the SOAP XML.");
            }
            if (businessClassPriceNodeList.getLength() == 0) {
                throw new IllegalArgumentException("BusinessClassPrice element not found in the SOAP XML.");
            }
            if (businessClassTicketCountNodeList.getLength() == 0) {
                throw new IllegalArgumentException("BusinessClassTicketCount element not found in the SOAP XML.");
            }
            if (firstClassPriceNodeList.getLength() == 0) {
                throw new IllegalArgumentException("FirstClassPrice element not found in the SOAP XML.");
            }
            if (firstClassTicketCountNodeList.getLength() == 0) {
                throw new IllegalArgumentException("FirstClassTicketCount element not found in the SOAP XML.");
            }
            
            LocalDateTime takeoffTime = LocalDateTime.parse(((Element) takeoffTimeNodeList.item(0)).getTextContent(), DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime landingTime = LocalDateTime.parse(((Element) landingTimeNodeList.item(0)).getTextContent(), DateTimeFormatter.ISO_DATE_TIME);
            String luggageRules = ((Element) luggageRulesNodeList.item(0)).getTextContent();
            float juniorDiscount = Float.parseFloat(((Element) juniorDiscountNodeList.item(0)).getTextContent());
            String departureAirportIata = ((Element) departureAirportIataNodeList.item(0)).getTextContent();
            String arrivalAirportIata = ((Element) arrivalAirportIataNodeList.item(0)).getTextContent();
            float economyClassPrice = Float.parseFloat(((Element) economyClassPriceNodeList.item(0)).getTextContent());
            int economyClassTicketCount = Integer.parseInt(((Element) economyClassTicketCountNodeList.item(0)).getTextContent());
            float businessClassPrice = Float.parseFloat(((Element) businessClassPriceNodeList.item(0)).getTextContent());
            int businessClassTicketCount = Integer.parseInt(((Element) businessClassTicketCountNodeList.item(0)).getTextContent());
            float firstClassPrice = Float.parseFloat(((Element) firstClassPriceNodeList.item(0)).getTextContent());
            int firstClassTicketCount = Integer.parseInt(((Element) firstClassTicketCountNodeList.item(0)).getTextContent());
            
            CreateFlightRequest request = new CreateFlightRequest(
                takeoffTime, landingTime, luggageRules, juniorDiscount,
                departureAirportIata, arrivalAirportIata, economyClassPrice,
                economyClassTicketCount, businessClassPrice, businessClassTicketCount,
                firstClassPrice, firstClassTicketCount
            );
            
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
