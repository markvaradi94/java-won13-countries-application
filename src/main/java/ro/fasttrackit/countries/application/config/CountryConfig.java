package ro.fasttrackit.countries.application.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "countries")
public class CountryConfig {
    private String fileLocation;
    private String continent;
    private long minPopulation;

    @PostConstruct
    void printConfig() {
        System.out.println("Country config = " + this);
    }
}
