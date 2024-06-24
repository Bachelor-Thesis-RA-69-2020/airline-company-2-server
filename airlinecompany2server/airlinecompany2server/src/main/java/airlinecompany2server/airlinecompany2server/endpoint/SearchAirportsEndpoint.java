package airlinecompany2server.airlinecompany2server.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.w3c.dom.NodeList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;

import airlinecompany2server.airlinecompany2server.endpoint.message.request.SearchAirportsRequest;
import airlinecompany2server.airlinecompany2server.endpoint.message.response.SearchAirportsResponse;
import airlinecompany2server.airlinecompany2server.model.domain.Airport;
import airlinecompany2server.airlinecompany2server.service.IAirportService;
import jakarta.xml.bind.JAXBElement;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Endpoint
public class SearchAirportsEndpoint extends BaseEndpoint {

    private static final String NAMESPACE_URI = "http://localhost:8082/";

    @Autowired
    private IAirportService airportService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchAirportsRequest")
    @ResponsePayload
    public JAXBElement<SearchAirportsResponse> searchAirports(@RequestPayload Source request) throws TransformerException {
        SearchAirportsRequest searchRequest = parseRequest(request);
        List<Airport> airports = airportService.search(searchRequest.getSearchFilter());

        SearchAirportsResponse response = new SearchAirportsResponse(airports);
        QName qname = new QName("SearchAirportsRequest");
        JAXBElement<SearchAirportsResponse> jaxbResponse = new JAXBElement<>(qname, SearchAirportsResponse.class, response);

        return jaxbResponse;
    }

    private SearchAirportsRequest parseRequest(Source source) {
        try {
            String xml = convertToXMLString(source);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

            doc.getDocumentElement().normalize();

            Element root = doc.getDocumentElement();

            NodeList searchFilterNodeList = root.getElementsByTagNameNS(NAMESPACE_URI, "SearchFilter");

            if (searchFilterNodeList.getLength() > 0) {
                Element searchFilterElement = (Element) searchFilterNodeList.item(0);
                String searchFilter = searchFilterElement.getTextContent();

                SearchAirportsRequest request = new SearchAirportsRequest(searchFilter);

                return request;
            } else {
                throw new IllegalArgumentException("SearchFilter element not found in the SOAP XML.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
