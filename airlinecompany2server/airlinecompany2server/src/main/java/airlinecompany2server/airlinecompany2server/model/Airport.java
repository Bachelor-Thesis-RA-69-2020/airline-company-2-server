package airlinecompany2server.airlinecompany2server.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.*;

@Document(collection = "airports")
public class Airport extends BaseEntity {
    @Id
    private String id;

    @NotNull(message = "Validation: Airport name is required")
    @NotEmpty(message = "Validation: Airport name cannot be empty")
    private String airportName;

    @NotNull(message = "Validation: IATA code is required")
    @NotEmpty(message = "Validation: IATA code cannot be empty")
    private String iataCode;

    @NotNull(message = "Validation: Latitude is required")
    @Min(value = -90, message = "Validation: Latitude must be at least -90")
    @Max(value = 90, message = "Validation: Latitude must be at most 90")
    private Float latitude;

    @NotNull(message = "Validation: Longitude is required")
    @Min(value = -180, message = "Validation: Longitude must be at least -180")
    @Max(value = 180, message = "Validation: Longitude must be at most 180")
    private Float longitude;

    @NotNull(message = "Validation: Elevation is required")
    private Float elevation;

    @NotNull(message = "Validation: Continent is required")
    @NotEmpty(message = "Validation: Continent cannot be empty")
    private String continentName;

    @NotNull(message = "Validation: Country is required")
    @NotEmpty(message = "Validation: Country cannot be empty")
    private String countryName;

    @NotNull(message = "Validation: Region is required")
    @NotEmpty(message = "Validation: Region cannot be empty")
    private String regionName;

    @NotNull(message = "Validation: Municipality is required")
    @NotEmpty(message = "Validation: Municipality cannot be empty")
    private String municipalityName;

    public Airport() {
    }

    public Airport(String airportName, String iataCode, Float latitude, Float longitude, Float elevation, String continentName, String countryName, String regionName, String municipalityName) {
        this.airportName = airportName;
        this.iataCode = iataCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
        this.continentName = continentName;
        this.countryName = countryName;
        this.regionName = regionName;
        this.municipalityName = municipalityName;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getIataCode() {
        return iataCode;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Float getElevation() {
        return elevation;
    }

    public String getContinentName() {
        return continentName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getMunicipalityName() {
        return municipalityName;
    }
}

