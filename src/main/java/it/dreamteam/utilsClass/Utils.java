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

        //Card
        Supplier<Card> cardSupplier = () -> new Card(
                userSupplier.get()
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

        //TravelDocument
        Supplier<Ticket> ticketSupplier = () -> new Ticket(
                false,
                userSupplier.get()
        );
        Supplier<Subscription> subscriptionSupplier = () -> new Subscription(
                getRandomEnum(Periodicity.class),
                cardSupplier.get()
        );

        for (int i = 0; i < numberOfElement; i++) {
            User user = userSupplier.get();
            userDAO.save(user);

            Reseller reseller = resellerSupplier.get();
            Reseller resellerMachine = resellerMachineSupplier.get();
            resellerDAO.save(reseller);
            resellerDAO.save(resellerMachine);

            Card card = cardSupplier.get();
            cardDAO.save(card);

            Route route = routeSupplier.get();
            routeDAO.save(route);

            Vehicle vehicle = vehicleSupplier.get();
            vehicleDAO.save(vehicle);


            TravelDocument ticket = ticketSupplier.get();
            TravelDocument subscription = subscriptionSupplier.get();
            travelDocumentDAO.save(ticket);
            travelDocumentDAO.save(subscription);
        }

        for (int j = 0; j < numberOfElement; j++) {
            Route routeForTrip = routeSupplier.get();
            routeDAO.save(routeForTrip);

            Vehicle vehicleForTrip = vehicleSupplier.get();
            vehicleDAO.save(vehicleForTrip);

            Trip trip = new Trip(vehicleForTrip, routeForTrip, generateRandomTime());
            tripDAO.save(trip);

        }
    }


}
