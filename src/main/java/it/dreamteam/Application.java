package it.dreamteam;

import it.dreamteam.DAO.ResellerDAO;
import it.dreamteam.DAO.TravelDocumentDAO;
import it.dreamteam.abstractClass.TravelDocument;
import it.dreamteam.utilsClass.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("transport");


    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        TravelDocumentDAO td = new TravelDocumentDAO(em);
        ResellerDAO rd = new ResellerDAO(em);

        System.out.println("Creo il DB!");
        Utils.createDatabase(10);

        System.out.println("**Test Query**");

        System.out.println("Verifica rapida della validità di un abbonamento in base al numero di tessera dell'utente controllato");
        td.findExpCard(1).forEach(System.out::println);
        System.out.println();

        System.out.println("Numero di biglietti e/o abbonamenti emessi in un dato periodo di tempo in totale e per punto di emissione");
        System.out.println(td.findNumberTicketsPlace(rd.findById(1), LocalDate.of(2024, 10, 1)));
        System.out.println();

        System.out.println("Numero di biglietti vidimati su un particolare mezzo o in totale in un periodo di tempo");

        //Oblitero dei ticket manualmente per verificare la query
        TravelDocument ticket = td.findid(2);
        td.obliterateTicket(ticket);

        TravelDocument ticket2 = td.findid(4);
        td.obliterateTicket(ticket2);

        System.out.println(td.numberOfTicketObliteratedInAVehicle(3));
        System.out.println(td.numberOfTicketObliteratedInAVehicle(5));
        System.out.println();

        em.close();
        emf.close();

    }
}
