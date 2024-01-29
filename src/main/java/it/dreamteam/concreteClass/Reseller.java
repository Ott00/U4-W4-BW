package it.dreamteam.concreteClass;

import javax.persistence.*;

@Entity
@Table(name = "resellers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "reseller_type")
@DiscriminatorValue("Authorized Reseller")
public class Reseller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;
    protected String name;
    protected String address;

    public Reseller() {
    }

    public Reseller(String name, String address) {
        this.name = name;
        this.address = address;
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
