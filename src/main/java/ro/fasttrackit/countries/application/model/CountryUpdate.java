package ro.fasttrackit.countries.application.model;

import lombok.Builder;

@Builder
public record CountryUpdate(
        String capital,
        Long area,
        Long population
) {
}
