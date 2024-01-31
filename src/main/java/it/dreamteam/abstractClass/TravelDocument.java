package it.dreamteam.abstractClass;

import it.dreamteam.concreteClass.Reseller;
import it.dreamteam.concreteClass.Trip;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "document_type")
@NamedQuery(name = "findExpCard", query = "SELECT t FROM TravelDocument t JOIN t.card c WHERE t.card.id = :card_id AND (c.expirationDate < CURRENT_DATE OR t.expirationDate < CURRENT_DATE)")
@NamedQuery(name = "numberEmissionPoint", query = "SELECT COUNT(a) FROM TravelDocument a WHERE a.emission_point = :emission_point AND a.emission_date < :emission_date ")
@NamedQuery(name = "numberOfTicketObliteratedInAVehicle", query = "SELECT COUNT(td) FROM TravelDocument td JOIN td.trips t JOIN t.vehicle v WHERE v.id = :vehicle_id AND td.obliterated = true")
public abstract class TravelDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    protected LocalDate emission_date;
    @ManyToOne
    @JoinColumn(name = "emission_point")
    protected Reseller emission_point;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "trip_travel_document",
            joinColumns = @JoinColumn(name = "travelDocument_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id")
    )
    private Set<Trip> trips = new HashSet<>();


    public TravelDocument() {
    }


    public TravelDocument(Reseller emission_point) {
        this.emission_date = LocalDate.now();
        this.emission_point = emission_point;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
