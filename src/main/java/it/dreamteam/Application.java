package it.dreamteam;

import it.dreamteam.DAO.TravelDocumentDAO;
import it.dreamteam.utilsClass.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("transport");


    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        TravelDocumentDAO td= new TravelDocumentDAO(em);


        Utils.createDatabase(10);


        td.findExpCard(1).forEach(System.out::println);

        em.close();
        emf.close();

    }
}
