package it.dreamteam.concreteClass;

import it.dreamteam.abstractClass.TravelDocument;

import javax.persistence.*;
import java.sql.Time;
import java.util.Set;

@Entity
@Table(name = "trips")
//@NamedQuery(name = "ticketveicolo",query = "SELECT COUNT(t.travelDocument.obliterated) FROM Trip WHERE t.travelDocument.obliterated = true ")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    private Time tripTime;

    @ManyToMany
    @JoinTable(
            name = "trip_travel_document",
            joinColumns = @JoinColumn(name = "travelDocument_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id")
    )
    private Set<TravelDocument> travelDocument;

    public Trip() {
    }

    public Trip(Vehicle vehicle, Route route, Time tripTime) {
        this.vehicle = vehicle;
        this.route = route;
        this.tripTime = tripTime;
    }

    public Long getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Time getTripTime() {
        return tripTime;
    }

    public void setTripTime(Time tripTime) {
        this.tripTime = tripTime;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", vehicle=" + vehicle +
                ", route destination=" + route.getEndAddress() +
                ", tripTime=" + tripTime +
                '}';
    }

    public Set<TravelDocument> getTravelDocument() {
        return travelDocument;
    }

    public void setTravelDocument(Set<TravelDocument> travelDocument) {
        this.travelDocument = travelDocument;
    }
}
