package ro.fasttrackit.countries.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import static jakarta.persistence.GenerationType.IDENTITY;
import static ro.fasttrackit.countries.application.model.Country.Fields.capital;

@Getter
@Setter
@Entity
@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String name;

    @JsonIgnore // doesn't add the field into the deserialized json
    @OneToOne(mappedBy = capital)
    private Country capitalOf;

    @JsonIgnore
    @ManyToOne  // --> COUNTRY_ID --> foreign key to COUNTRY(ID)
    private Country country;
}
