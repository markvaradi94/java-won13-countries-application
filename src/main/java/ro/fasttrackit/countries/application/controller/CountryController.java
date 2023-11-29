package ro.fasttrackit.countries.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.countries.application.exception.ResourceNotFoundException;
import ro.fasttrackit.countries.application.model.*;
import ro.fasttrackit.countries.application.service.CountryService;

import java.util.List;

@RestController
@RequestMapping("countries")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:4200")
public class CountryController {
    private final CountryService service;

    @GetMapping
    List<Country> getAll(CountryFilters filters) {
        return service.filterCountries(filters);
    }

    @GetMapping("{id}")
    Country getById(@PathVariable long id) {
        return service.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(id), id));
    }

    @PostMapping
    Country addCountry(@RequestBody Country country) {
        return service.add(country);
    }

    @PatchMapping("{id}")
    Country updateCountry(@PathVariable long id, @RequestBody CountryUpdate country) {
        return service.update(id, country);
    }

    @PutMapping("{id}")
    Country replaceCountry(@PathVariable long id, @RequestBody Country country) {
        return service.replace(id, country);
    }

    @DeleteMapping("{id}")
    Country deleteCountry(@PathVariable long id) {
        return service.delete(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find country with id %s".formatted(id), id));
    }

    @PostMapping("{countryId}/cities")
    Country addCity(@PathVariable long countryId, @RequestBody City newCity) {
        return service.addCityToCountry(countryId, newCity);
    }

    @GetMapping("{countryId}/cities")
    List<City> getCities(@PathVariable long countryId) {
        return service.getCitiesForCountry(countryId);
    }

    @PostMapping("{countryId}/neighbours")
    Country addNeighbour(@PathVariable long countryId, @RequestBody NeighbourRequest neighbour) {
        return service.addNeighbourToCountry(countryId, neighbour);
    }
}
