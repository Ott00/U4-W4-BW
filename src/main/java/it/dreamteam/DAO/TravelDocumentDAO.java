package it.dreamteam.DAO;


import it.dreamteam.abstractClass.TravelDocument;
import it.dreamteam.concreteClass.*;
import it.dreamteam.enumClass.Periodicity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class TravelDocumentDAO {

    private final EntityManager em;

    public TravelDocumentDAO(EntityManager em) {
        this.em = em;
    }

    public TravelDocument createTicket(Reseller reseller, User user) {
        TravelDocument ticket = new Ticket(reseller, user);
        save(ticket);
        return ticket;
    }

    public TravelDocument createSubscription(Reseller reseller, Periodicity periodicity, Card card) {
        TravelDocument subscription = new Subscription(reseller, periodicity, card);
        save(subscription);
        return subscription;
    }

    public void save(TravelDocument re) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(re);
            transaction.commit();
//            System.out.println("aggiunto document " + re.getId());
        } catch (Exception e) {
            em.getTransaction().rollback();
//                System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public TravelDocument findById(long id) {
        return em.find(TravelDocument.class, id);
    }

    public void delete(long id) {
        TravelDocument found = this.findById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("eliminato Codice: " + id);
        } else {
            System.out.println("non trovato");
        }
    }


    //questo metodo va a cambiarmi il valore nel db del ticket da false a true
    public void obliterateTicket(TravelDocument ticket) {
        TravelDocument found = this.findById(ticket.getId());
        if (found instanceof Ticket) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            //ho bisogno di un cast dato che found è un traveldocument
            ((Ticket) found).setObliterated(true);
            em.merge(found);
            transaction.commit();

            System.out.println("Ticket obliterato");
        } else System.out.println("Gli abbonamenti non possono essere obliterati!");
    }

    //questo metodo controlla nell'if se ticket(un traveldocument) è di tipo Ticket, se si allora mi vaso a prendere
    //il valore del ticket oblitereted, se non è ancora stato obliterato allora entra in gioco obliterateTicket(ticket)
    public void checkIfObliteratedAndObliterate(TravelDocument ticket) {
        if (ticket instanceof Ticket) {
            boolean obliterated = ((Ticket) ticket).isObliterated();
            if (!obliterated) {
                obliterateTicket(ticket);
                System.out.println("Biglietto obliterato");
            } else System.out.println("Biglietto già obliterato, comprane un altro!");
        }
    }

    public void checkCardOrSubscriptionExpired(Card card) {
        if (findExpCard(card.getId()).isEmpty()) {
            System.out.println("Benvenuto a bordo");
        } else System.out.println("Il tuo abbonamento o la tessera sono scaduti!");
    }

    //il valore di ritorno sarà un Long perchè io sto cercando il count quindi solo un numero
    public Long findNumberTicketsPlace(Reseller emission_point, LocalDate emission_date) {
        TypedQuery<Long> query = em.createNamedQuery("numberEmissionPoint", Long.class);
        query.setParameter("emission_point", emission_point);
        query.setParameter("emission_date", emission_date);
        return query.getSingleResult();

    }

    public List<TravelDocument> findExpCard(long id) {
        TypedQuery<TravelDocument> query = em.createNamedQuery("findExpCard", TravelDocument.class);
        query.setParameter("card_id", id);
        return query.getResultList();
    }

    public Long numberOfTicketObliteratedInAVehicle(long vehicle_id) {
        TypedQuery<Long> query = em.createNamedQuery("numberOfTicketObliteratedInAVehicle", Long.class);
        query.setParameter("vehicle_id", vehicle_id);
        return query.getSingleResult();
    }

}
