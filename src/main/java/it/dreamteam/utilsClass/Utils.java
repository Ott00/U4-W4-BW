package it.dreamteam.utilsClass;

import com.github.javafaker.Faker;
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
        int hour = ThreadLocalRandom.current().nextInt(0, 24);
        int minute = ThreadLocalRandom.current().nextInt(0, 60);

        return Time.valueOf(LocalTime.of(hour, minute, 0));
    }

    public static void createDatabase(int numberOfElement) {
        EntityManager entityManager = emf.createEntityManager();
        Faker faker = new Faker(Locale.ITALY);
        Random random = new Random();


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
                getRandomEnum(VehicleStatus.class)
        );

        //Maintenance
        Supplier<Maintenance> maintenanceSupplier = () -> new Maintenance(
                LocalDate.now().plusDays(7),
                faker.lorem().sentence(10),
                vehicleSupplier.get()
        );

        //Trip
        Supplier<Trip> tripSupplier = () -> new Trip(
                vehicleSupplier.get(),
                routeSupplier.get(),
                generateRandomTime()
        );

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
            
        }
    }


}
