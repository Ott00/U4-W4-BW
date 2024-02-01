package it.dreamteam.DAO;

import it.dreamteam.concreteClass.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.TransactionalException;

public class VehicleDAO {
    EntityManager em;

    public VehicleDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Vehicle vehicle) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(vehicle);
            transaction.commit();
//            System.out.println("Veicolo di trasporto n. " + vehicle.getId() + " aggiunto con successo!");
        } catch (TransactionalException te) {
            System.err.println(te.getMessage());
        }
    }

    public Vehicle findById(long id) {
        return em.find(Vehicle.class, id);
    }

    public void deleteById(long id) {
        Vehicle found = this.findById(id);
        if (found != null) {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Veicolo di trasporto n. " + id + " eliminato dal db.");
        } else {
            System.out.println("Veicolo di trasporto n. " + id + " non trovato.");
        }
    }


}
