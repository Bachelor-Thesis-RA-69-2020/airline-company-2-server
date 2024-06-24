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

import airlinecompany2server.airlinecompany2server.endpoint.message.request.CreateDiscountRequest;
import airlinecompany2server.airlinecompany2server.endpoint.message.response.CreateDiscountResponse;
import airlinecompany2server.airlinecompany2server.service.IDiscountService;
import jakarta.xml.bind.JAXBElement;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Endpoint
public class CreateDiscountEndpoint extends BaseEndpoint {
    private static final String NAMESPACE_URI = "http://localhost:8082/";

    @Autowired
    private IDiscountService discountService;
 
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateDiscountRequest")
    @ResponsePayload
    public JAXBElement<CreateDiscountResponse> createDiscount(@RequestPayload Source request) throws TransformerException {
        CreateDiscountRequest createRequest = parseRequest(request);
        String result = discountService.create(createRequest.getFlightCode(), createRequest.mapToDiscountDomain());

        CreateDiscountResponse response = new CreateDiscountResponse(result);
        QName qname = new QName("CreateDiscountResponse");
        JAXBElement<CreateDiscountResponse> jaxbResponse = new JAXBElement<CreateDiscountResponse>(qname, CreateDiscountResponse.class, response);

        return jaxbResponse;
    }

    private CreateDiscountRequest parseRequest(Source source) {
        try {
            String xml = convertToXMLString(source);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            NodeList flightCodeTimeNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "FlightCode");
            NodeList fromNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "From");
            NodeList toNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "To");
            NodeList offValueNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "OffValue");

            if (flightCodeTimeNodeList.getLength() == 0) {
                throw new IllegalArgumentException("FlightCode element not found in the SOAP XML.");
            }
            if (fromNodeList.getLength() == 0) {
                throw new IllegalArgumentException("From element not found in the SOAP XML.");
            }
            if (toNodeList.getLength() == 0) {
                throw new IllegalArgumentException("To element not found in the SOAP XML.");
            }
            if (offValueNodeList.getLength() == 0) {
                throw new IllegalArgumentException("OffValue element not found in the SOAP XML.");
            }
            
            String flightCode = ((Element) flightCodeTimeNodeList.item(0)).getTextContent();
            LocalDateTime from = LocalDateTime.parse(((Element) fromNodeList.item(0)).getTextContent(), DateTimeFormatter.ISO_DATE_TIME);
            LocalDateTime to = LocalDateTime.parse(((Element) toNodeList.item(0)).getTextContent(), DateTimeFormatter.ISO_DATE_TIME);
            float offValue = Float.parseFloat(((Element) offValueNodeList.item(0)).getTextContent());
            
            CreateDiscountRequest request = new CreateDiscountRequest(flightCode, from, to, offValue);
            
            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
