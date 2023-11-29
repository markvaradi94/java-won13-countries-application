package ro.fasttrackit.countries.application.model;

import lombok.Builder;

@Builder
public record CountryUpdate(
        String name,
        String capital,
        String continent,
        Long area,
        Long population
) {
}
