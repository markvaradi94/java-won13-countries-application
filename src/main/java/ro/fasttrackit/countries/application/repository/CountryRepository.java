package ro.fasttrackit.countries.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.countries.application.model.Continent;
import ro.fasttrackit.countries.application.model.Country;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    // #1 --> method naming
    List<Country> findAllByContinent(Continent continent);

    // #2 --> jpql query
    @Query("SELECT c FROM Country c WHERE c.continent=:continent")
    List<Country> findByContinentJPQL(@Param("continent") Continent continent);

    // #3 --> native sql query
    @Query(value = "SELECT * FROM COUNTRY WHERE CONTINENT=:continent", nativeQuery = true)
    List<Country> findByContinentNativeQuery(@Param("continent") String continent);

    List<Country> findAllByNameLike(String name);

    List<Country> findAllByCapitalContaining(String name);

    List<Country> findAllByPopulationGreaterThanEqual(long minPopulation);

    List<Country> findAllByPopulationLessThanEqual(long maxPopulation);

    List<Country> findAllByAreaGreaterThanEqual(long minArea);

    List<Country> findAllByAreaLessThanEqual(long maxArea);
}
