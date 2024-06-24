package airlinecompany2server.airlinecompany2server.endpoint.message.response;

import java.util.List;
import java.util.stream.Collectors;

import airlinecompany2server.airlinecompany2server.model.Airport;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Airport")
public class AirportResponse {
    @XmlElement(name = "AirportName")
    private String airportName;

    @XmlElement(name = "IataCode")
    private String iataCode;

    @XmlElement(name = "Latitude")
    private Float latitude;

    @XmlElement(name = "Longitude")
    private Float longitude;

    @XmlElement(name = "Elevation")
    private Float elevation;

    @XmlElement(name = "ContinentName")
    private String continentName;

    @XmlElement(name = "CountryName")
    private String countryName;

    @XmlElement(name = "RegionName")
    private String regionName;

    @XmlElement(name = "MunicipalityName")
    private String municipalityName;

    public AirportResponse() {
    }

    public AirportResponse(Airport airport) {
        this.airportName = airport.getAirportName();
        this.iataCode = airport.getIataCode();
        this.latitude = airport.getLatitude();
        this.longitude = airport.getLongitude();
        this.elevation = airport.getElevation();
        this.continentName = airport.getContinentName();
        this.countryName = airport.getCountryName();
        this.regionName = airport.getRegionName();
        this.municipalityName = airport.getMunicipalityName();
    }

    public static final AirportResponse mapToResponse(Airport airport) {
        return new AirportResponse(airport);
    }

    public static final List<AirportResponse> mapToResponseList(List<Airport> airports) {
        return airports.stream()
                       .map(AirportResponse::mapToResponse)
                       .collect(Collectors.toList());
    }
}

