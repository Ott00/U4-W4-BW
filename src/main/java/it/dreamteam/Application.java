package it.dreamteam;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {

    private static final EntityManagerFactory emf= Persistence.createEntityManagerFactory("transport");


    public static void main(String[] args) {
        EntityManager em=emf.createEntityManager();
        em.close();
        emf.close();

    }
}
