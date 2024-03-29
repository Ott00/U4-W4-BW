package it.dreamteam.DAO;

import it.dreamteam.concreteClass.Route;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.transaction.TransactionalException;
import java.util.List;

public class RouteDAO {
    EntityManager em;

    public RouteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Route route) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(route);
            transaction.commit();
//            System.out.println("Itinerario n. " + route.getId() + " aggiunto con successo!");
        } catch (TransactionalException te) {
            System.err.println(te.getMessage());
        }
    }

    public Route findById(long id) {
        return em.find(Route.class, id);
    }

    public void deleteById(long id) {
        Route found = this.findById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Itinerario n. " + id + " eliminato dal db.");
        } else {
            System.out.println("Itinerario n. " + id + " non trovato.");
        }
    }

    //il valore di ritorno di questa lista sarà un array di oggetti dato che non mi interessa tutta la
    // tabella Route ma solo alcune colonne
    public List<Object[]> findAllRoutes() {
        TypedQuery<Object[]> query = em.createNamedQuery("findAllRoutes", Object[].class);
        return query.getResultList();
    }
}
