package ro.fasttrackit.countries.application.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.countries.application.exception.ResourceNotFoundException;
import ro.fasttrackit.countries.application.model.Continent;
import ro.fasttrackit.countries.application.model.Country;
import ro.fasttrackit.countries.application.model.CountryUpdate;
import ro.fasttrackit.countries.application.reader.CountryReader;
import ro.fasttrackit.countries.application.repository.CountryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryReader reader;
    //    private final List<Country> countries;
    private final CountryRepository repository;

    @PostConstruct
    void init() {
        repository.saveAll(reader.readCountries());
//        this.countries.addAll(reader.readCountries());
    }

    public List<Country> filterCountries(String continent) {
        return continent == null ? listAllCountries() : listCountriesByContinent(continent);
    }

    public List<Country> listAllCountries() {
        return repository.findAll();
    }

    public List<Country> listCountriesByContinent(String continentName) {
        Continent continent = Continent.of(continentName);
        return repository.findAll().stream()
                .filter(country -> country.getContinent() == continent)
                .toList();
    }

    public Optional<Country> findById(long id) {
        return repository.findAll().stream()
                .filter(country -> country.getId() == id)
                .findFirst();
    }

    public Country add(Country newCountry) {
        return repository.save(newCountry);
    }

    public Country update(long id, CountryUpdate countryUpdate) {
        Country country = getOrThrow(id);
        Country updatedCountry = country
                .withCapital(countryUpdate.capital() == null ? country.getCapital() : countryUpdate.capital())
                .withArea(countryUpdate.area() == null ? country.getArea() : countryUpdate.area())
                .withPopulation(countryUpdate.population() == null ? country.getPopulation() : countryUpdate.population());

        return repository.save(updatedCountry);
    }

    public Country replace(long id, Country newCountry) {
        Country countryToReplace = getOrThrow(id);
        return repository.save(newCountry.withId(countryToReplace.getId()));
    }

    public Optional<Country> delete(long id) {
        Optional<Country> countryOptional = findById(id);
        countryOptional.ifPresent(repository::delete);
        return countryOptional;
    }

    public List<String> allCountryNames() {
        return repository.findAll().stream()
                .map(Country::getName)
                .toList();
    }

    public List<Country> countriesInContinentWithMinPopulation(String continentName, long minPopulation) {
        return repository.findAll().stream()
                .filter(country -> country.getContinent() == Continent.of(continentName))
                .filter(country -> country.getPopulation() >= minPopulation)
                .toList();
    }

    public List<Country> countriesByNeighbours(String include, String exclude) {
        return repository.findAll().stream()
                .filter(country -> checkNeighbours(include, exclude, country))
                .toList();
    }

    public List<String> countryNeighbours(long countryId) {
        return getOrThrow(countryId).getNeighbours();
    }

    public Long countryPopulation(long countryId) {
        return getOrThrow(countryId).getPopulation();
    }

    public String countryCapital(long countryId) {
        return getOrThrow(countryId).getCapital();
    }

    private boolean checkNeighbours(String include, String exclude, Country country) {
        return country.getNeighbours().contains(include.toUpperCase()) && !country.getNeighbours().contains(exclude.toUpperCase());
    }

    private Country getOrThrow(long countryId) {
        return findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(countryId), countryId));
    }
}
