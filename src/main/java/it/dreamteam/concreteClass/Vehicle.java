package it.dreamteam.concreteClass;

import it.dreamteam.enumClass.VehicleStatus;
import it.dreamteam.enumClass.VehicleType;

import javax.persistence.*;
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
}
