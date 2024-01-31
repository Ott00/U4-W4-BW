package it.dreamteam;

import it.dreamteam.DAO.*;
import it.dreamteam.abstractClass.TravelDocument;
import it.dreamteam.concreteClass.Trip;
import it.dreamteam.utilsClass.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("transport");


    public static <Time> void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        TravelDocumentDAO td = new TravelDocumentDAO(em);
        ResellerDAO rd = new ResellerDAO(em);
        TripDAO tr = new TripDAO(em);
        VehicleDAO vd = new VehicleDAO(em);

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

        TripDAO tripDAO = new TripDAO(em);
        RouteDAO routeDAO = new RouteDAO(em);
        Trip trip = new Trip(vd.findById(3), routeDAO.findById(2), new java.sql.Time(2, 3, 3));
        tripDAO.save(trip);
        System.out.println("Numero di volte che un mezzo percorre una tratta e del tempo effettivo di percorrenza di ogni tratta");
        tr.timeTrip(3).forEach(result -> System.out.println("Tempo del viaggio: " + result[0] + ", ID veicolo: " + result[1]));
        tr.timeTrip(5).forEach(result -> System.out.println("Tempo del viaggio: " + result[0] + ", ID veicolo: " + result[1]));

        System.out.println(tr.Tripnumber(3));
        em.close();
        emf.close();

    }
}
