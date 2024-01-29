package it.dreamteam.concreteClass;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "trips")
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
}
