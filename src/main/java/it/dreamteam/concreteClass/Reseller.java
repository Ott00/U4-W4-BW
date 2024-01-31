package it.dreamteam.concreteClass;

import it.dreamteam.abstractClass.TravelDocument;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "resellers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "reseller_type")
@DiscriminatorValue("Authorized Reseller")
//@NamedQuery(
//        name = "numeroPuntoEmissione",
//        query = "SELECT COUNT(t) FROM TravelDocument t WHERE t.emission_point.id = :id AND t.emission_date <= :emission_date"
//)
public class Reseller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    protected String name;
    protected String address;
    @OneToMany(mappedBy = "emission_point")
    protected List<TravelDocument> travelDocuments;

    public Reseller() {
    }

    public Reseller(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public List<TravelDocument> getTravelDocuments() {
        return travelDocuments;
    }

    public void setTravelDocuments(List<TravelDocument> travelDocuments) {
        this.travelDocuments = travelDocuments;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Reseller{" +
                "id=" + id +
                ", address='" + address + '\'' +
                '}';
    }
}
