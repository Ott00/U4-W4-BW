package it.dreamteam.DAO;

import it.dreamteam.concreteClass.Card;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CardDAO {
    private final EntityManager em;

    public CardDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Card cr) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(cr);
            transaction.commit();
            System.out.println("aggiunto card "+ cr.getId());
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }

    public Card findid(long id) {
        return em.find(Card.class, id);
    }

    public void delete(long id) {
        Card found = this.findid(id);
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
}
