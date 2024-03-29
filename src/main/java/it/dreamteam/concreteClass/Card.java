package it.dreamteam.concreteClass;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalDate expirationDate;

    //perche un utente pò avere più tessere (magari una è scaduta e una no)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //più abbonamenti possono essere associati ad una tessera (es. due abb. mensili scaduti ed uno settimanale attivo)
    @OneToMany(mappedBy = "card")
    private List<Subscription> subscriptions;

    public Card() {
    }

    public Card(User user) {
        this.expirationDate = LocalDate.now().plusYears(1);
        this.user = user;
    }

    //devo inserire almeno una card scaduta per farla comparire nella query, perciò devo creare un nuovo costruttore e inserire
    //la scadenza a mano
    public Card(LocalDate expirationDate, User user) {
        this.expirationDate = expirationDate;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", expirationDate=" + expirationDate +
                ", user=" + user.getName() + " " + user.getSurname() +
                ", subscriptions=" + subscriptions.size() +
                '}';
    }
}
