package airlinecompany2server.airlinecompany2server.endpoint.message.request;

import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "Passenger")
public class PassengerRequest {

    @XmlElement(name = "FullName", required = true)
    protected String fullName;

    @XmlElement(name = "BirthDate", required = true)
    protected LocalDateTime birthDate;

    @XmlElement(name = "PassportID", required = true)
    protected String passportId;

    public PassengerRequest(String fullName, LocalDateTime birthDate, String passportId) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.passportId = passportId;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public String getPassportId() {
        return passportId;
    }
}
