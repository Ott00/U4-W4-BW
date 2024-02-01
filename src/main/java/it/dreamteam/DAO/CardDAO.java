package it.dreamteam.DAO;

import it.dreamteam.concreteClass.Card;
import it.dreamteam.concreteClass.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CardDAO {
    private final EntityManager em;

    public CardDAO(EntityManager em) {
        this.em = em;
    }

    public Card createCard(User user) {
        Card card = new Card(user);
        save(card);
        return card;
    }

    public void save(Card cr) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(cr);
            transaction.commit();
//            System.out.println("aggiunto card " + cr.getId());
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }

    public Card findById(long id) {
        return em.find(Card.class, id);
    }

    public void delete(long id) {
        Card found = this.findById(id);
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

//    public Subscription validità (long id){
//
////        Card found=this.findid(id);
////        if (found!=null && found.getExpirationDate()!=null){
////
////
////        }
//    }
}
