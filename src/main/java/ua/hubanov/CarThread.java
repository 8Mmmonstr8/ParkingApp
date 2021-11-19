package ua.hubanov;

import java.util.Random;

public class CarThread implements Runnable {

    private Parking parking;
    private Car car;

    public CarThread(Parking parking, Car car) {
        this.parking = parking;
        this.car = car;
    }

    @Override
    public void run() {
        int count = 0;
        Thread.currentThread().setName(car.getName());
        parking.enterParking(car);
        parking.parkACar(getRandomParkingTime());
        parking.leftParking(car);
    }

    private int getRandomParkingTime() {
        Random random = new Random();
        return random.nextInt(60) * 1000;
    }
}
