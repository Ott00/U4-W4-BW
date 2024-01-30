package it.dreamteam.DAO;


import it.dreamteam.abstractClass.TravelDocument;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TravelDocumentDAO {

        private final EntityManager em;

        public TravelDocumentDAO(EntityManager em) {
            this.em = em;
        }

        public void save(TravelDocument re) {
            try {
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
                em.persist(re);
                transaction.commit();
                System.out.println("aggiunto");
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.out.println(e.getMessage());
            }
        }

        public TravelDocument findid(long id){
            return em.find(TravelDocument.class,id);
        }
        public void delete(long id){
            TravelDocument found=this.findid(id);
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

//    public List<TravelDocument> findnumberticketsplace(Reseller emission_point, LocalDate emission_date) {
//        TypedQuery<TravelDocument> query = em.createNamedQuery("numeropuntoemissione", TravelDocument.class);
//        query.setParameter("emission_point", emission_point);
//        query.setParameter("emission_date", emission_date);
//        return query.getResultList();
//
//    }

    }