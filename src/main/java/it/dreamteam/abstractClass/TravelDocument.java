package it.dreamteam.abstractClass;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "document_type")
public abstract class TravelDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    protected LocalDate emission_date;
    protected LocalDate emission_point;

    public TravelDocument() {
    }

    public LocalDate getEmission_date() {
        return emission_date;
    }

    public void setEmission_date(LocalDate emission_date) {
        this.emission_date = emission_date;
    }

    public LocalDate getEmission_point() {
        return emission_point;
    }

    public void setEmission_point(LocalDate emission_point) {
        this.emission_point = emission_point;
    }
}
