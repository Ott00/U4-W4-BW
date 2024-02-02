package it.dreamteam.concreteClass;

import it.dreamteam.abstractClass.TravelDocument;
import it.dreamteam.enumClass.VehicleStatus;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trips")
//va a selezionare iltempo effettivo di percorrenza e l'id del veicolo in base ad una veicolo
@NamedQuery(name = "timeTrip", query = "SELECT t.tripTime,t.vehicle.id FROM Trip t WHERE t.vehicle.id=:id ")
//va a contare il numero di volte che un veicolo percorre una route e le ragruppa in base all'id della rotta
@NamedQuery(name = "tripNumber", query = "SELECT t.route.id,COUNT(t) FROM Trip t WHERE t.vehicle.id=:id AND t.vehicle.vehicleStatus='IN_SERVIZIO' GROUP BY t.route.id ")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    //un viaggio può essere percorso più volte da un veicolo
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    //molti viaggi possono essere fatti su una rotta
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;
    private Time tripTime;

    //molti viaggi possono essere percorsi da molti user
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "trip_travel_document",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "travelDocument_id")
    )
    private List<TravelDocument> travelDocument = new ArrayList<>();

    public Trip() {
    }

    public Trip(Vehicle vehicle, Route route, Time tripTime) {
        this.vehicle = vehicle;
        this.route = route;
        //nel caso il mezzo è in mununtenzione, la tratta non viene fatta perciò il tempo effettivo diventerà null
        if (vehicle.getVehicleStatus() == VehicleStatus.IN_MANUTENZIONE) {
            this.tripTime = null;
        } else {
            this.tripTime = tripTime;
        }
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

    public List<TravelDocument> getTravelDocument() {
        return travelDocument;
    }

    public void setTravelDocument(List<TravelDocument> travelDocument) {
        this.travelDocument = travelDocument;
    }
}
