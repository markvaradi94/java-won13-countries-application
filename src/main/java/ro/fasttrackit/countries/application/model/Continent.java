package ro.fasttrackit.countries.application.model;

import java.util.Arrays;

public enum Continent {
    ASIA,
    AFRICA,
    AMERICAS,
    EUROPE,
    OCEANIA;

    public static Continent of(String name) {
        return Arrays.stream(Continent.values())
                .filter(continent -> continent.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find continent with name %s".formatted(name)));
    }
}
