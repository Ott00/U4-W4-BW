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
                faker.address().fullAddress(),
                faker.address().fullAddress(),
                generateRandomTime()
        );

        //Vehicle
        Supplier<Vehicle> vehicleSupplier = () -> new Vehicle(
                getRandomEnum(VehicleType.class),
                faker.number().numberBetween(40, 60),
                VehicleStatus.IN_SERVIZIO
        );

        //Maintenance
        Vehicle vehicleInMaintenance = new Vehicle(getRandomEnum(VehicleType.class),
                faker.number().numberBetween(40, 60),
                VehicleStatus.IN_MANUTENZIONE);

        vehicleDAO.save(vehicleInMaintenance);

        Maintenance maintenance = new Maintenance(
                LocalDate.now().plusDays(7),
                faker.lorem().sentence(10),
                vehicleInMaintenance
        );

        maintenanceDAO.save(maintenance);

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

            //Rotte esistenti ma non percorse
            Route route = routeSupplier.get();
            routeDAO.save(route);

            Vehicle vehicle = vehicleSupplier.get();
            vehicleDAO.save(vehicle);

            //User Subscription
            User user = userSupplier.get();
            userDAO.save(user);

            //User Ticket
            User user2 = userSupplier.get();
            userDAO.save(user2);

            //Card
            Card card = new Card(user);
            cardDAO.save(card);

            TravelDocument ticket = new Ticket(reseller, user2);

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

            Trip trip = new Trip(vehicleForTrip, routeForTrip, generateRandomTime());
            tripDAO.save(trip);

            subscription.getTrips().add(trip);
            ticket.getTrips().add(trip);

            travelDocumentDAO.save(ticket);
            travelDocumentDAO.save(subscription);

        }
    }
}
