package it.dreamteam.utilsClass;

import com.github.javafaker.Faker;
import it.dreamteam.DAO.*;
import it.dreamteam.abstractClass.TravelDocument;
import it.dreamteam.concreteClass.*;
import it.dreamteam.enumClass.Periodicity;
import it.dreamteam.enumClass.ResellerMachineStatus;
import it.dreamteam.enumClass.VehicleStatus;
import it.dreamteam.enumClass.VehicleType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class Utils {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("transport");

    public static <T extends Enum<?>> T getRandomEnum(Class<T> enumeration) {
        Random random = new Random();
        T[] values = enumeration.getEnumConstants();
        return values[random.nextInt(values.length)];
    }

    public static Time generateRandomTime() {
        int hour = ThreadLocalRandom.current().nextInt(0, 5);
        int minute = ThreadLocalRandom.current().nextInt(0, 60);

        return Time.valueOf(LocalTime.of(hour, minute, 0));
    }

    public static void createDatabase(int numberOfElement) {
        EntityManager entityManager = emf.createEntityManager();
        Faker faker = new Faker(Locale.ITALY);
        Random random = new Random();

        //DAO
        CardDAO cardDAO = new CardDAO(entityManager);
        MaintenanceDAO maintenanceDAO = new MaintenanceDAO(entityManager);
        ResellerDAO resellerDAO = new ResellerDAO(entityManager);
        RouteDAO routeDAO = new RouteDAO(entityManager);
        TravelDocumentDAO travelDocumentDAO = new TravelDocumentDAO(entityManager);
        TripDAO tripDAO = new TripDAO(entityManager);
        UserDAO userDAO = new UserDAO(entityManager);
        VehicleDAO vehicleDAO = new VehicleDAO(entityManager);


        //User
        Supplier<User> userSupplier = () -> new User(
                faker.name().firstName(),
                faker.name().lastName(),
                faker.date().birthday(12, 80).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        );

        //Reseller
        Supplier<Reseller> resellerSupplier = () -> new Reseller(
                faker.name().firstName(),
                faker.address().fullAddress()
        );

        //Reseller Machine
        Supplier<ResellerMachine> resellerMachineSupplier = () -> new ResellerMachine(
                faker.name().firstName(),
                faker.address().fullAddress(),
                getRandomEnum(ResellerMachineStatus.class)
        );

        //Route
        Supplier<Route> routeSupplier = () -> new Route(
                faker.address().streetName() + " " + faker.address().buildingNumber(),
                faker.address().streetName() + " " + faker.address().buildingNumber(),
                generateRandomTime()
        );

        //Vehicle
        Supplier<Vehicle> vehicleSupplier = () -> new Vehicle(
                getRandomEnum(VehicleType.class),
                faker.number().numberBetween(40, 60),
                getRandomEnum(VehicleStatus.class)
        );

        //Maintenance
//        Vehicle vehicleInMaintenance = new Vehicle(getRandomEnum(VehicleType.class),
//                faker.number().numberBetween(40, 60),
//                VehicleStatus.IN_MANUTENZIONE);

//        vehicleDAO.save(vehicleInMaintenance);

//        Maintenance maintenance = new Maintenance(
//                LocalDate.now().plusDays(7),
//                faker.lorem().sentence(10),
//                vehicleInMaintenance
//        );

//        maintenanceDAO.save(maintenance);

        //card scaduta test
        User user1 = userSupplier.get();
        userDAO.save(user1);

        Card card1 = new Card(LocalDate.of(2023, 10, 4), user1);
        cardDAO.save(card1);

        Reseller reseller1 = resellerSupplier.get();
        resellerDAO.save(reseller1);

        TravelDocument travelDocument1 = new Subscription(
                reseller1,
                getRandomEnum(Periodicity.class),
                card1
        );
        travelDocumentDAO.save(travelDocument1);

        for (int j = 0; j < numberOfElement; j++) {
            Reseller reseller = resellerSupplier.get();
            Reseller resellerMachine = resellerMachineSupplier.get();
            resellerDAO.save(reseller);
            resellerDAO.save(resellerMachine);

            User user = userSupplier.get();
            userDAO.save(user);

            User user2 = userSupplier.get();
            userDAO.save(user2);

            Card card = new Card(user);
            cardDAO.save(card);

            TravelDocument ticket1 = new Ticket(reseller, user2);

            TravelDocument subscription1 = new Subscription(
                    reseller,
                    getRandomEnum(Periodicity.class),
                    card
            );

            travelDocumentDAO.save(ticket1);
            travelDocumentDAO.save(subscription1);
        }


        for (int j = 0; j < numberOfElement; j++) {
            Reseller reseller = resellerSupplier.get();
            Reseller resellerMachine = resellerMachineSupplier.get();
            resellerDAO.save(reseller);
            resellerDAO.save(resellerMachine);

            //Rotte esistenti ma non percorse
            Route route = routeSupplier.get();
            routeDAO.save(route);

            //User Subscription
            User user = userSupplier.get();
            userDAO.save(user);

            //User Ticket
            User user2 = userSupplier.get();
            userDAO.save(user2);

            //Card
            Card card = new Card(user);
            cardDAO.save(card);

            //Ticket
            TravelDocument ticket = new Ticket(reseller, user2);

            //Subscription
            TravelDocument subscription = new Subscription(
                    reseller,
                    getRandomEnum(Periodicity.class),
                    card
            );

            //Rotte che sono state effettivamente percorse
            Route routeForTrip = routeSupplier.get();
            routeDAO.save(routeForTrip);

            Vehicle vehicleForTrip = vehicleSupplier.get();
            vehicleDAO.save(vehicleForTrip);

            //nel caso il veicolo avesse problemi allora lo registriamo in manuntenzione
            if (vehicleForTrip.getVehicleStatus() == VehicleStatus.IN_MANUTENZIONE) {

                Maintenance maintenance1 = new Maintenance(
                        LocalDate.now().plusDays(7),
                        faker.lorem().sentence(10),
                        vehicleForTrip
                );
                maintenanceDAO.save(maintenance1);
            }

            Trip trip = new Trip(vehicleForTrip, routeForTrip, generateRandomTime());
            tripDAO.save(trip);

            //aggiungo la subscription ad un viaggio
            trip.getTravelDocument().add(subscription);

            //aggiungo il ticket ad un viaggio nel caso questo non è ancora stato obliterato
            if (((Ticket) ticket).isObliterated() == false) {
                trip.getTravelDocument().add(ticket);
                ((Ticket) ticket).setObliterated(true);
            } else {
                System.out.println("Il ticket è già stato obliterato, devi comprarne un'altro");
            }

            travelDocumentDAO.save(ticket);
            travelDocumentDAO.save(subscription);

        }
    }
}
