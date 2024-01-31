package it.dreamteam.DAO;

import it.dreamteam.concreteClass.Trip;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.TransactionalException;

public class TripDAO {
    EntityManager em;

    public TripDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Trip trip) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(trip);
            transaction.commit();
            System.out.println("Viaggio n. " + trip.getId() + " aggiunto con successo!");
        } catch (TransactionalException te) {
            System.err.println(te.getMessage());
        }
    }

    public Trip findById(long id) {
        return em.find(Trip.class, id);
    }

    public void deleteById(long id) {
        Trip found = this.findById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Viaggio n. " + id + " rimosso dal db.");
        } else {
            System.out.println("Viaggio n. " + id + " non trovato.");
        }
    }


}
