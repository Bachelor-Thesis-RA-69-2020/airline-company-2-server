package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BookFlightResponse")
public class BookFlightResponse {

    @XmlElement(name = "Message")
    private String message;

    public BookFlightResponse() {
    }

    public BookFlightResponse(String message) {
        this.message = message;
    }
}
