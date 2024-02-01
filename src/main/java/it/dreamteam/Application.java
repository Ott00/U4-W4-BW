package it.dreamteam;

import it.dreamteam.DAO.*;
import it.dreamteam.utilsClass.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Application {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("transport");


    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        TravelDocumentDAO td = new TravelDocumentDAO(em);
        ResellerDAO rd = new ResellerDAO(em);
        TripDAO tr = new TripDAO(em);
        VehicleDAO vd = new VehicleDAO(em);
        UserDAO ud = new UserDAO(em);
        CardDAO cd = new CardDAO(em);
        RouteDAO rtd = new RouteDAO(em);

        System.out.println("Creo il DB!");
        Utils.createDatabase(5);
        System.out.println();

//        Reseller reseller = rd.createReseller("Tabacchino Epicode", "Via dei Magazzini Generali, 16, 00154 Roma RM");
//
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Benvenuto, come ti chiami?");
//        String name = scanner.nextLine();
//        System.out.println("Come fai di cognome?");
//        String surname = scanner.nextLine();
//        User user = ud.createUser(name, surname);
//
//        System.out.println(name + " cosa desideri acquistare?\n1) Un biglietto\n2) Un abbonamento");
//        int choice = Integer.parseInt(scanner.nextLine());
//
//        TravelDocument ticket = null;
//        Card card = null;
//        TravelDocument subscriptionWeekly;
//        TravelDocument subscriptionMonthly;
//
//        switch (choice) {
//            case 1:
//                System.out.println("Ecco a te, viene 5€");
//                System.out.println("*Pagamento...*");
//                System.out.println("Arrivederci e grazie!");
//                ticket = td.createTicket(reseller, user);
//                break;
//            case 2:
//                System.out.println("Creiamo la tessere allora...\n");
//                card = cd.createCard(user);
//
//                System.out.println("Un abbonamento quindi, di quale durata? \n1) Settimanale\n2) Mensile ");
//                int choice2 = Integer.parseInt(scanner.nextLine());
//
//                switch (choice2) {
//                    case 1:
//                        System.out.println("Un abbonamento settimanale viene 15€");
//                        subscriptionWeekly = td.createSubscription(reseller, Periodicity.SETTIMANALE, card);
//                        System.out.println("*Pagamento...*");
//                        System.out.println("Arrivederci e grazie!");
//                        break;
//                    case 2:
//                        System.out.println("Un abbonamento mensile viene 50€");
//                        subscriptionMonthly = td.createSubscription(reseller, Periodicity.MENSILE, card);
//                        System.out.println("*Pagamento...*");
//                        System.out.println("Arrivederci e grazie!");
//                        break;
//                    default:
//                        System.out.println("Scelta non valida!");
//                        break;
//                }
//        }
//
//        System.out.println("'" + name + " arriva alla stazione...'");
//        System.out.println("Dove vuoi andare?");
//        rtd.findAllRoutes().forEach(result -> System.out.println(result[0] + ") " + result[1]));
//        int idRoute = Integer.parseInt(scanner.nextLine());
//        System.out.println("Il bus per " + rtd.findById(idRoute).getEndAddress() + " arriverà a breve!");
//
//        //Utilizziamo un veicolo già esistente
//        Vehicle vehicle = vd.findById(2);
//
//        System.out.println("Il " + vehicle.getVehicleType().toString().toLowerCase() + " numero " + vehicle.getId() + " è arrivato...\n");
//        System.out.println("'" + name + " sale sul bus'");
//        System.out.println("'Il controllore chiede...'");
//        System.out.println("Hai un...  \n1)Biglietto \n2)Abbonamento");
//        int choice3 = Integer.parseInt(scanner.nextLine());
//
//        switch (choice3) {
//            case 1:
//                System.out.println("Bene, obliteralo pure alla macchinetta lì");
//                td.checkIfObliteratedAndObliterate(ticket);
//                break;
//            case 2:
//                System.out.println("Ok, controlliamo l'abbonamento...");
//                assert card != null;
//                td.checkCardOrSubscriptionExpired(card);
//                break;
//            default:
//                System.out.println("Scelta non valida!");
//                break;
//        }
//
//        Trip trip = tr.createTrip(vehicle, rtd.findById(idRoute));
//        System.out.println("Dopo " + trip.getTripTime() + " siamo arrivati a " + rtd.findById(idRoute).getEndAddress());
//        System.out.println("Grazie per averci scelto, arrivederci!");

        System.out.println("**Test Query**");

//        System.out.println("Verifica rapida della validità di un abbonamento in base al numero di tessera dell'utente controllato");
//        td.findExpCard(1).forEach(System.out::println);
//        System.out.println();
//
//        System.out.println("Numero di biglietti e/o abbonamenti emessi in un dato periodo di tempo in totale e per punto di emissione");
//        System.out.println(td.findNumberTicketsPlace(rd.findById(1), LocalDate.of(2024, 10, 1)));
//        System.out.println();
//
//        System.out.println("Numero di biglietti vidimati su un particolare mezzo o in totale in un periodo di tempo");
//
//        //Oblitero dei ticket manualmente per verificare la query
//        TravelDocument ticket = td.findById(2);
//        td.obliterateTicket(ticket);
//
//        TravelDocument ticket2 = td.findById(4);
//        td.obliterateTicket(ticket2);
//
//        System.out.println(td.numberOfTicketObliteratedInAVehicle(3));
//        System.out.println(td.numberOfTicketObliteratedInAVehicle(5));
//        System.out.println();
//
//
//        System.out.println("Numero di volte che un mezzo percorre una tratta e del tempo effettivo di percorrenza di ogni tratta");
//        tr.timeTrip(3).forEach(result -> System.out.println("Tempo del viaggio: " + result[0] + ", ID veicolo: " + result[1]));
//        tr.tripNumber(3).forEach(result->System.out.println("ID rotta "+result[0] + ", numero volte percorso " + result[1]));
//        scanner.close();
        em.close();
        emf.close();
    }
}
