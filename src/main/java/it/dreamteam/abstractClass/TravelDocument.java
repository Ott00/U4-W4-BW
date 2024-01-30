package it.dreamteam.abstractClass;

import it.dreamteam.concreteClass.Reseller;
import it.dreamteam.concreteClass.Trip;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "document_type")
@NamedQuery(name = "numeropuntoemissione",query = "SELECT COUNT(a) FROM TravelDocument a WHERE a.emission_point LIKE :emission_point AND a.emission_date=:emission_date ")
//@NamedQuery(name ="find_expiration",query = "SELECT t FROM TravelDocument t JOIN t.card WHERE t.card_id=:card_id")
public abstract class TravelDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    protected LocalDate emission_date;
    @ManyToOne
    @JoinColumn(name="emission_point")
    protected Reseller emission_point;

    @ManyToMany
    @JoinTable(
            name = "trip_travel_document",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "travelDocument_id")
    )
    private Set<Trip> trips;



    public TravelDocument() {
    }

    public void setEmission_point(Reseller emission_point) {
        this.emission_point = emission_point;
    }

    public LocalDate getEmission_date() {
        return emission_date;
    }

    public void setEmission_date(LocalDate emission_date) {
        this.emission_date = emission_date;
    }

    public Reseller getEmission_point() {
        return emission_point;
    }

    public Set<Trip> getTrips() {
        return trips;
    }

    public void setTrips(Set<Trip> trips) {
        this.trips = trips;
    }
}
