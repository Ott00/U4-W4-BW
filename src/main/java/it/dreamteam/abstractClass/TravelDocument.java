package it.dreamteam.abstractClass;

import it.dreamteam.concreteClass.Reseller;
import it.dreamteam.concreteClass.Trip;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "document_type")
//la query findexpcard mi va a cercare se una card o abbonamento scaduto in basa al id della card
@NamedQuery(name = "findExpCard", query = "SELECT t FROM TravelDocument t JOIN t.card c WHERE t.card.id = :card_id AND (c.expirationDate < CURRENT_DATE OR t.expirationDate < CURRENT_DATE)")
//questa query mi va a contare il numero di traveldocument emessi da un emissionpoint in un certo periodo di tempo
@NamedQuery(name = "numberEmissionPoint", query = "SELECT COUNT(a) FROM TravelDocument a WHERE a.emission_point = :emission_point AND a.emission_date < :emission_date ")
//in questa query mi vado a contare il numero di traveldocument usando una JOIN per spostarmi alla tabella Trips, di nuovo join per spostarmi su Vehicle dove potrò prendermi il su id e usarlo come
//parametro insieme al obliterated che deve essere true
@NamedQuery(name = "numberOfTicketObliteratedInAVehicle", query = "SELECT COUNT(td) FROM TravelDocument td JOIN td.trips t JOIN t.vehicle v WHERE v.id = :vehicle_id AND td.obliterated = true")
public abstract class TravelDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    protected LocalDate emission_date;

    //molti travel document possono essere emessi da un unico punto
    @ManyToOne
    @JoinColumn(name = "emission_point")
    protected Reseller emission_point;

    //molti traveldocument possono essere usati in molti viaggi(mi riferisco agli abbonamenti)
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "trip_travel_document",
            joinColumns = @JoinColumn(name = "travelDocument_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id")
    )
    private List<Trip> trips = new ArrayList<>();


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

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }
}
