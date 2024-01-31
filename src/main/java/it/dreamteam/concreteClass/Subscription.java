package it.dreamteam.concreteClass;

import it.dreamteam.abstractClass.TravelDocument;
import it.dreamteam.enumClass.Periodicity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Subscription extends TravelDocument {
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    public Subscription() {
    }

    public Subscription(Reseller emission_point, Periodicity periodicity, Card card) {
        super(emission_point);
        this.expirationDate = periodicity == Periodicity.MENSILE ? LocalDate.now().plusMonths(1) : LocalDate.now().plusWeeks(1);
        this.periodicity = periodicity;
        this.card = card;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "expirationDate=" + expirationDate +
                ", periodicity=" + periodicity +
                ", card id=" + card.getId() +
                ", id=" + id +
                ", emission_date=" + emission_date +
                ", emission_point=" + emission_point +
                '}';
    }
}
