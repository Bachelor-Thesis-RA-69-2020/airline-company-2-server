package airlinecompany2server.airlinecompany2server.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.NodeList;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;

import airlinecompany2server.airlinecompany2server.endpoint.message.request.FindFlightByCodeRequest;
import airlinecompany2server.airlinecompany2server.endpoint.message.response.FindFlightByCodeResponse;
import airlinecompany2server.airlinecompany2server.model.Flight;
import airlinecompany2server.airlinecompany2server.service.IFlightService;
import jakarta.xml.bind.JAXBElement;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Endpoint
public class FindFlightByCodeEndpoint extends BaseEndpoint {

    private static final String NAMESPACE_URI = "http://localhost:8082/";

    @Autowired
    private IFlightService flightService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FindFlightByCodeRequest")
    @ResponsePayload
    public JAXBElement<FindFlightByCodeResponse> searchAirports(@RequestPayload Source request) throws TransformerException {
        FindFlightByCodeRequest findRequest = parseRequest(request);
        Flight flight = flightService.findByCode(findRequest.getCode());

        FindFlightByCodeResponse response = new FindFlightByCodeResponse(flight);
        QName qname = new QName("FindFlightByCodeResponse");
        JAXBElement<FindFlightByCodeResponse> jaxbResponse = new JAXBElement<FindFlightByCodeResponse>(qname, FindFlightByCodeResponse.class, response);

        return jaxbResponse;
    }

    private FindFlightByCodeRequest parseRequest(Source source) {
        try {
            String xml = convertToXMLString(source);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            NodeList codeNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "Code");

            if (codeNodeList.getLength() == 0) {
                throw new IllegalArgumentException("Code element not found in the SOAP XML.");
            } 

            String code = ((Element) codeNodeList.item(0)).getTextContent();

            FindFlightByCodeRequest request = new FindFlightByCodeRequest(code);

            return request;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
