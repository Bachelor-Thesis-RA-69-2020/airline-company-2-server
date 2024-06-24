package airlinecompany2server.airlinecompany2server.configuration;

import airlinecompany2server.airlinecompany2server.model.domain.Airport;
import airlinecompany2server.airlinecompany2server.repository.AirportRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AirportsDataConfiguration {

    private final AirportRepository airportRepository;

    @Autowired
    public AirportsDataConfiguration(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @PostConstruct
    public void init() throws IOException, CsvValidationException {
        if (!(airportRepository.count() == 0)) {
            System.out.println("Skipping airport data migration.");
            return;
        }
        
        loadAirportsDataFromCSV();
    }

    private void loadAirportsDataFromCSV() throws IOException, CsvValidationException {
        System.out.println("Starting airport data migration.");

        Resource resource = new ClassPathResource("data/airports.csv");
        InputStream inputStream = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        List<Airport> airports = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(reader)) {
            String[] values;
            csvReader.readNext();
            while ((values = csvReader.readNext()) != null) {
                if ("large_airport".equals(values[2]) && validateRow(values)) {
                    Airport airport = new Airport(
                            values[3],
                            values[13],
                            Float.parseFloat(values[4]),
                            Float.parseFloat(values[5]),
                            Float.parseFloat(values[6]),
                            values[7],
                            values[8],
                            values[9],
                            values[10]
                    );
                    airports.add(airport);
                }
            }
        }
        airportRepository.saveAll(airports);
        System.out.println("Airport data migration completed.");
    }

    private boolean validateRow(String[] values) {
        return values[3] != null && !values[3].isEmpty()
                && values[13] != null && !values[13].isEmpty()
                && values[4] != null && !values[4].isEmpty()
                && values[5] != null && !values[5].isEmpty()
                && values[6] != null && !values[6].isEmpty()
                && values[7] != null && !values[7].isEmpty()
                && values[8] != null && !values[8].isEmpty()
                && values[9] != null && !values[9].isEmpty()
                && values[10] != null && !values[10].isEmpty();
    }
}