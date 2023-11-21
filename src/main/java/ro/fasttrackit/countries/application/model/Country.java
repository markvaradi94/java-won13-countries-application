package ro.fasttrackit.countries.application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@With
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String name;
    private String capital;
    private long population;
    private long area;
    private Continent continent;

    @Transient
    private List<String> neighbours;
}
