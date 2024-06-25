package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import java.time.LocalDateTime;

import airlinecompany2server.airlinecompany2server.endpoint.message.adapter.LocalDateTimeAdapter;
import airlinecompany2server.airlinecompany2server.model.Flight;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Schedule")
public class FlightScheduleResponse {

    @XmlElement(name = "TakeOffTime")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime takeoffTime;

    @XmlElement(name = "LandingTime")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime landingTime;

    @XmlElement(name = "Duration")
    private Integer duration;

    public FlightScheduleResponse() {
    }

    public FlightScheduleResponse(Flight flight) {
        this.takeoffTime = flight.getTakeoffTime();
        this.landingTime = flight.getLandingTime();
        this.duration = flight.getDuration();
    }
    
}
