package ro.fasttrackit.countries.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.countries.application.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
