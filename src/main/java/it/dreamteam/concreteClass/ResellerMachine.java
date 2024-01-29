package it.dreamteam.concreteClass;

import it.dreamteam.enumClass.ResellerMachineStatus;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Reseller Machine")
public class ResellerMachine extends Reseller {
    private ResellerMachineStatus resellerMachineStatus;

    public ResellerMachine() {
    }

    public ResellerMachine(String name, String address, ResellerMachineStatus resellerMachineStatus) {
        super(name, address);
        this.resellerMachineStatus = resellerMachineStatus;
    }

    public ResellerMachineStatus getResellerMachineStatus() {
        return resellerMachineStatus;
    }

    public void setResellerMachineStatus(ResellerMachineStatus resellerMachineStatus) {
        this.resellerMachineStatus = resellerMachineStatus;
    }

    @Override
    public String toString() {
        return "ResellerMachine{" +
                "resellerMachineStatus=" + resellerMachineStatus +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
