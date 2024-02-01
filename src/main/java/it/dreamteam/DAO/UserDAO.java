package it.dreamteam.DAO;

import it.dreamteam.concreteClass.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;

public class UserDAO {
    private final EntityManager em;

    public UserDAO(EntityManager em) {
        this.em = em;
    }

    public User createUser(String name, String surname) {
        LocalDate birthday = LocalDate.now().minusYears(24);
        User user = new User(name, surname, birthday);
        save(user);
        return user;
    }

    public void save(User ur) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(ur);
            transaction.commit();
//            System.out.println("aggiunto user" + ur.getId());
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }

    public User findid(long id) {
        return em.find(User.class, id);
    }

    public void delete(long id) {
        User found = this.findid(id);
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
