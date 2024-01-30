package it.dreamteam;

import it.dreamteam.utilsClass.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("transport");


    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        Utils.createDatabase(10);

        em.close();
        emf.close();

    }
}
