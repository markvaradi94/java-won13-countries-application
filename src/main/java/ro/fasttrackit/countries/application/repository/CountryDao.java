package ro.fasttrackit.countries.application.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.countries.application.model.Continent;
import ro.fasttrackit.countries.application.model.Country;
import ro.fasttrackit.countries.application.model.CountryFilters;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static ro.fasttrackit.countries.application.model.Country.Fields.*;

@Repository
@RequiredArgsConstructor
public class CountryDao {  // DAO == Data Access Object
    private final EntityManager entityManager;

    public List<Country> filterCountries(CountryFilters filters) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Country> query = criteriaBuilder.createQuery(Country.class);
        Root<Country> country = query.from(Country.class);
        List<Predicate> predicates = new ArrayList<>();

        ofNullable(filters.continent())
                .ifPresent(filterContinent -> predicates.add(criteriaBuilder.equal(country.get(continent), Continent.of(filterContinent))));

        ofNullable(filters.name())
                .ifPresent(countryName -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(country.get(name)), "%" + countryName.toLowerCase() + "%")));

        ofNullable(filters.minPopulation())
                .ifPresent(minPopulation -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(country.get(population), minPopulation)));

        ofNullable(filters.maxPopulation())
                .ifPresent(maxPopulation -> predicates.add(criteriaBuilder.lessThanOrEqualTo(country.get(population), maxPopulation)));

        ofNullable(filters.minArea())
                .ifPresent(minArea -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(country.get(area), minArea)));

        ofNullable(filters.maxArea())
                .ifPresent(maxArea -> predicates.add(criteriaBuilder.lessThanOrEqualTo(country.get(area), maxArea)));

        query.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Country> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }
}
