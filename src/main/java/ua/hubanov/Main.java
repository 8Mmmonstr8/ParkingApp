package ua.hubanov;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        Parking parking = new Parking();

        ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            Car car = new Car("Car " + i);
            Runnable worker = new CarThread(parking, car);
            service.execute(worker);
        }

        service.shutdown();
    }
}
