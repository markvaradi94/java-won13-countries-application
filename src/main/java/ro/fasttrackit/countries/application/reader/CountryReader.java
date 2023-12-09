package ro.fasttrackit.countries.application.reader;

import ro.fasttrackit.countries.application.model.Country;

import java.util.List;

public interface CountryReader {
    List<Country> readCountries();
}
