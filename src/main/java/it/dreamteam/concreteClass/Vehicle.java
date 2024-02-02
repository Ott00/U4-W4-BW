package it.dreamteam.concreteClass;

import it.dreamteam.abstractClass.TravelDocument;
import it.dreamteam.enumClass.VehicleStatus;
import it.dreamteam.enumClass.VehicleType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "vehicle_type")
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private int capacity;
    @Column(name = "vehicle_status")
    @Enumerated(EnumType.STRING)
    private VehicleStatus vehicleStatus;

    //un veicolo può essere stato in manuntenzione più volte
    @OneToMany(mappedBy = "vehicle")
    private List<Maintenance> maintenances;

    //un veicolo può fare molti viaggi
    @OneToMany(mappedBy = "vehicle")
    private List<Trip> trips;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL)
    private List<TravelDocument> travelDocuments = new ArrayList<>();

    public Vehicle() {
    }

    public Vehicle(VehicleType vehicleType, int capacity, VehicleStatus vehicleStatus) {
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.vehicleStatus = vehicleStatus;
    }

    public Long getId() {
        return id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public List<Maintenance> getMaintenances() {
        return maintenances;
    }

    public void setMaintenances(List<Maintenance> maintenances) {
        this.maintenances = maintenances;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", vehicleType=" + vehicleType +
                ", capacity=" + capacity +
                ", vehicleStatus=" + vehicleStatus +
                ", maintenances=" + maintenances +
                ", trips=" + trips.size() +
                '}';
    }

    public List<TravelDocument> getTravelDocuments() {
        return travelDocuments;
    }

    public void setTravelDocuments(List<TravelDocument> travelDocuments) {
        this.travelDocuments = travelDocuments;
    }


    public void addTravelDocument(TravelDocument travelDocument) {
        this.travelDocuments.add(travelDocument);
        travelDocument.setVehicle(this);
    }
}
