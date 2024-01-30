package it.dreamteam.DAO;

import it.dreamteam.concreteClass.Maintenance;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.TransactionalException;

public class MaintenanceDAO {
    EntityManager em;

    public MaintenanceDAO(EntityManager em) {
        this.em = em;
    }

    public void saveMaintenance (Maintenance maintenance) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(maintenance);
            transaction.commit();
            System.out.println("Itinerario n. " + maintenance.getId() + " aggiunto con successo!");
        } catch (TransactionalException te) {
            System.err.println(te.getMessage());
        }
    }

    public Maintenance findById(long id){
        return em.find(Maintenance.class,id);
    }

    public void deleteById(long id){
        Maintenance found=this.findById(id);
        if (found != null){
            EntityTransaction transaction= em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("Manutenzione n. " + id + " rimossa dal db.");
        }else {
            System.out.println("Manutenzione n. " + id + " non trovata.");
        }
    }


}
