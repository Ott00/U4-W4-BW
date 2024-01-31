package it.dreamteam;

import it.dreamteam.DAO.ResellerDAO;
import it.dreamteam.DAO.TravelDocumentDAO;
import it.dreamteam.DAO.TripDAO;
import it.dreamteam.abstractClass.TravelDocument;
import it.dreamteam.utilsClass.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("transport");


    public static <Time> void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        TravelDocumentDAO td = new TravelDocumentDAO(em);
        ResellerDAO rd = new ResellerDAO(em);
        TripDAO tr=new TripDAO(em);

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
        List<Object[]> resultList = tr.timeTrip(3);
        for (Object[] result : resultList) {
            Time tripTime = (Time) result[0]; // Ottieni il tempo del viaggio
            Long tripCount = (Long) result[1]; // Ottieni il conteggio dei viaggi

            System.out.println("Tempo del viaggio: " + tripTime + ", Conteggio dei viaggi: " + tripCount);
        }



        em.close();
        emf.close();

    }
}
