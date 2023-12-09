package ro.fasttrackit.countries.application.reader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fasttrackit.countries.application.config.CountryConfig;
import ro.fasttrackit.countries.application.model.City;
import ro.fasttrackit.countries.application.model.Continent;
import ro.fasttrackit.countries.application.model.Country;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@RequiredArgsConstructor
public class CountryFileReader implements CountryReader {
    public static long ID = 1;
    private final CountryConfig config;

    public List<Country> readCountries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(config.getFileLocation()))) {
            return reader.lines()
                    .map(this::parseCountry)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Country parseCountry(String line) {
        String[] tokens = line.split("[|]");
        return Country.builder()
                .id(ID++)
                .name(tokens[0])
                .capital(City.builder()
                        .name(tokens[1])
                        .build())
                .population(Long.parseLong(tokens[2]))
                .area(Long.parseLong(tokens[3]))
                .continent(Continent.of(tokens[4]))
                .neighbours(new ArrayList<>())
                .build();
    }
}
