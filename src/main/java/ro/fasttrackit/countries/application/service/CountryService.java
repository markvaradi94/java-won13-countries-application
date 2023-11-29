package ro.fasttrackit.countries.application.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fasttrackit.countries.application.exception.ResourceNotFoundException;
import ro.fasttrackit.countries.application.model.*;
import ro.fasttrackit.countries.application.reader.CountryReader;
import ro.fasttrackit.countries.application.repository.CityRepository;
import ro.fasttrackit.countries.application.repository.CountryDao;
import ro.fasttrackit.countries.application.repository.CountryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryReader reader;
    private final CityRepository cityRepository;
    private final CountryDao dao;  // DAO --> data access object
    private final CountryRepository countryRepository;

    @PostConstruct
    void init() {
        List<Country> countries = countryRepository.saveAll(reader.readCountries());
        countries.forEach(country -> country.getCapital().setCountry(country));

        cityRepository.saveAll(countries.stream()
                .map(Country::getCapital)
                .toList());
    }

    public List<Country> filterCountries(CountryFilters filters) {
        return dao.filterCountries(filters);
    }

    public List<Country> listAllCountries() {
        return countryRepository.findAll();
    }

    public List<Country> listCountriesByContinent(String continentName) {
        return countryRepository.findAllByContinent(Continent.of(continentName));
    }

    public Optional<Country> findById(long id) {
        return countryRepository.findById(id);
    }

    public Country add(Country newCountry) {
        return countryRepository.save(newCountry);
    }

    public Country addCityToCountry(long id, City newCity) {
        Country countryToUpdate = getOrThrow(id);
        newCity.setCountry(countryToUpdate);
        countryToUpdate.getCities().add(newCity);
        cityRepository.save(newCity);
        return countryRepository.save(countryToUpdate);
    }

    public List<City> getCitiesForCountry(long countryId) {
        return cityRepository.findAllByCountryId(countryId);
    }

    public Country addNeighbourToCountry(long id, NeighbourRequest request) {
        Country countryToUpdate = getOrThrow(id);
        Country neighbour = Country.builder()
                .name(request.name())
                .area(request.area())
                .population(request.population())
                .continent(countryToUpdate.getContinent())
                .neighbours(new ArrayList<>())
                .build();

        neighbour.getNeighbours().add(countryToUpdate);
        countryRepository.save(neighbour);
        countryToUpdate.getNeighbours().add(neighbour);
        return countryRepository.save(countryToUpdate);
    }

    public Country update(long id, CountryUpdate countryUpdate) {
        Country country = getOrThrow(id);
        Country updatedCountry = country
                .withName(countryUpdate.name() == null ? country.getName() : countryUpdate.name())
                .withCapital(countryUpdate.capital() == null ? country.getCapital() : country.getCapital().withName(countryUpdate.capital()))
                .withContinent(countryUpdate.continent() == null ? country.getContinent() : Continent.of(countryUpdate.continent()))
                .withArea(countryUpdate.area() == null ? country.getArea() : countryUpdate.area())
                .withPopulation(countryUpdate.population() == null ? country.getPopulation() : countryUpdate.population());

        return countryRepository.save(updatedCountry);
    }

    public Country replace(long id, Country newCountry) {
        Country countryToReplace = getOrThrow(id);
        return countryRepository.save(newCountry.withId(countryToReplace.getId()));
    }

    public Optional<Country> delete(long id) {
        Optional<Country> countryOptional = findById(id);
        countryOptional.ifPresent(countryRepository::delete);
        return countryOptional;
    }

    public List<String> allCountryNames() {
        return countryRepository.findAll().stream()
                .map(Country::getName)
                .toList();
    }

    public List<Country> countriesInContinentWithMinPopulation(String continentName, long minPopulation) {
        return countryRepository.findAll().stream()
                .filter(country -> country.getContinent() == Continent.of(continentName))
                .filter(country -> country.getPopulation() >= minPopulation)
                .toList();
    }

    public Long countryPopulation(long countryId) {
        return getOrThrow(countryId).getPopulation();
    }

    private Country getOrThrow(long countryId) {
        return findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(countryId), countryId));
    }
}
