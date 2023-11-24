package ro.fasttrackit.countries.application.model;

import lombok.Builder;

@Builder
public record CountryFilters(
        String continent,
        String name,
        String capital,
        Long minPopulation,
        Long maxPopulation,
        Long minArea,
        Long maxArea
) {
}
