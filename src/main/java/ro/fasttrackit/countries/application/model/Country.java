package ro.fasttrackit.countries.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;
import static ro.fasttrackit.countries.application.model.City.Fields.country;

@Getter
@Setter
@With
@Entity
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @OneToOne(cascade = ALL)
    private City capital;

    @Enumerated(value = STRING)
    private Continent continent;

    @OneToMany(mappedBy = country, fetch = EAGER)
    private List<City> cities;

    @JsonIgnore
    @ManyToMany(fetch = EAGER)
    private List<Country> neighbours;

    private String name;
    private long population;
    private long area;
}
