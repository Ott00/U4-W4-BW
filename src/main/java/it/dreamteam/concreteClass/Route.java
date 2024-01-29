package it.dreamteam.concreteClass;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "start_address")
    private String startAddress;

    @Column(name = "end_address")
    private String endAddress;
    @Column(name = "average_time")
    private Time averageTime;
    @OneToMany(mappedBy = "route")
    private List<Trip> trips;

    public Route() {
    }

    public Route(String startAddress, String endAddress, Time averageTime) {
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.averageTime = averageTime;
    }

    public Long getId() {
        return id;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public Time getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Time averageTime) {
        this.averageTime = averageTime;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", startAddress='" + startAddress + '\'' +
                ", endAddress='" + endAddress + '\'' +
                ", averageTime=" + averageTime +
                ", trips=" + trips.size() +
                '}';
    }
}
