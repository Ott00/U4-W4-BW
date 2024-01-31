package it.dreamteam.DAO;

import it.dreamteam.concreteClass.Reseller;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ResellerDAO {
    private final EntityManager em;

    public ResellerDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Reseller re) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(re);
            transaction.commit();
            System.out.println("aggiunto reseller "+ re.getId());
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        }
    }

    public Reseller findid(long id){
        return em.find(Reseller.class,id);
    }
    public void delete(long id){
        Reseller found=this.findid(id);
        if (found!=null){
            EntityTransaction transaction= em.getTransaction();
            transaction.begin();
            em.remove(found);
            transaction.commit();
            System.out.println("eliminato Codice: "+id);
        }else {
            System.out.println("non trovato");
        }
    }
}
