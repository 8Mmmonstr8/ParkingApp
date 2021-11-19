package ua.hubanov;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Parking {

    private static final int maxCarsInParking = 6;
    private static final int minCarsInParking = 0;
    private static final int parkingSpacesCount = 5;

    private final Semaphore parkingSpaces = new Semaphore(parkingSpacesCount);
    private List<Car> parking;
    private volatile int carsCounter = 0;

    public Parking() {
        parking = new ArrayList<>();
    }

    public synchronized boolean enterParking(Car car) {
        try {
            if (carsCounter < maxCarsInParking) {
                notifyAll();
                parking.add(car);
                System.out.println(Thread.currentThread().getName() + " has entered parking");
                carsCounter++;
            } else {
                System.out.println("There is no place in parking for a " + Thread.currentThread().getName());
                wait();
                return false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    void parkACar(int time) {
        try {
            while(parkingSpaces.availablePermits() == 0) {
                System.out.println(Thread.currentThread().getName() + " trying to find empty space...");
                Thread.sleep(1000);
            }
            parkingSpaces.acquire();
            System.out.println(Thread.currentThread().getName() + " has parked");
            Thread.sleep(time);
            parkingSpaces.release();
            System.out.println(Thread.currentThread().getName() + " has unparked");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean leftParking(Car car) {
        try {
            if (carsCounter > minCarsInParking) {
                carsCounter--;
                System.out.println(Thread.currentThread().getName() + " left parking lot");
                parking.remove(car);
                notifyAll();
                return true;
            }
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
