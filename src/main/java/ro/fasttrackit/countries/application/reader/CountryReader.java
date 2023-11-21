package ro.fasttrackit.countries.application.reader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ro.fasttrackit.countries.application.config.CountryConfig;
import ro.fasttrackit.countries.application.model.Continent;
import ro.fasttrackit.countries.application.model.Country;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Getter
@Component
//@RequiredArgsConstructor
public class CountryReader {
    public static long ID = 1;
    private final CountryConfig config;
//    private final String inputFile;

//    public CountryReader(@Value("${countries.fileLocation:default-countries.txt}") String inputFile) {
//        this.inputFile = inputFile;
//    }


    public CountryReader(CountryConfig config) {
        this.config = config;
    }

    public List<Country> readCountries() {
        try (BufferedReader reader = new BufferedReader(new FileReader(config.getFileLocation()))) {
            return reader.lines()
                    .map(this::parseCountry)
//                    .filter(country -> country.continent() == Continent.of(config.getContinent()))
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
                .capital(tokens[1])
                .population(Long.parseLong(tokens[2]))
                .area(Long.parseLong(tokens[3]))
                .continent(Continent.of(tokens[4]))
                .neighbours(tokens.length == 6 ? neighbourList(tokens[5]) : List.of())
                .build();
    }

    private List<String> neighbourList(String neighboursString) {
        String[] tokens = neighboursString.split("~");
        return Arrays.asList(tokens);
    }
}
