package ro.fasttrackit.countries.application.model;

import lombok.Builder;

@Builder
public record NeighbourRequest(
        String name,
        Long population,
        Long area
) {
}
