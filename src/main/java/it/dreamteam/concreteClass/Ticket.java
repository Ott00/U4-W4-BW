package it.dreamteam.concreteClass;

import it.dreamteam.abstractClass.TravelDocument;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ticket extends TravelDocument {
    private boolean obliterated;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Ticket() {
    }


    public Ticket(Reseller emission_point, User user) {
        super(emission_point);
        this.obliterated = false;
        this.user = user;

    }

    public boolean isObliterated() {
        return obliterated;
    }

    public void setObliterated(boolean obliterated) {
        this.obliterated = obliterated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "obliterated=" + obliterated +
                ", user=" + user.getName() + " " + user.getSurname() +
                ", id=" + id +
                ", emission_date=" + emission_date +
                ", emission_point=" + emission_point +
                '}';
    }
}
