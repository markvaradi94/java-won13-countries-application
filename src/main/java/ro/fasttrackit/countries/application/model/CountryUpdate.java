package ro.fasttrackit.countries.application.model;

import lombok.Builder;

@Builder
public record CountryUpdate(
        Long area,
        Long population
) {
}
