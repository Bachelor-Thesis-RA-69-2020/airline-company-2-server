package airlinecompany2server.airlinecompany2server.endpoint.message.request;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "FindFlightByCodeRequest")
public class FindFlightByCodeRequest {

    @XmlElement(name = "Code", required = true)
    protected String code;  

    public FindFlightByCodeRequest(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
