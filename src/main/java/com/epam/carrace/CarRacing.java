package com.epam.carrace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CarRacing {

    public static void main(String[] args) {

        List<CompletableFuture<Car>> tasks = new ArrayList<CompletableFuture<Car>>();

        for(int i=0; i<20;i++){
            tasks.add(createRace(i));
        }

        CompletableFuture<Void> done = CompletableFuture
                .allOf(tasks.toArray(new CompletableFuture[tasks.size()]));

        CompletableFuture<List<Car>> allCompletableFuture = done.thenApply(future -> {
            return tasks.stream()
                    .map(task -> task.join())
                    .collect(Collectors.toList());
        });

        CompletableFuture completableFuture = allCompletableFuture.thenApply(t -> {
            return t.stream().collect(Collectors.toList());
        });

        List<Car> finalResult = (List<Car>)completableFuture.join();

        finalResult.forEach(t-> System.out.println("Finished Car " + t.getCarNumber() + " with avarge speed "+ t.getAvarageSpeed()) );
    }

    private static CompletableFuture<Car> createRace(final int carNumber){
        return CompletableFuture.supplyAsync(() -> {

            Random rand = new Random();

            Car car = new Car();
            car.setCarNumber(carNumber);
            for(int i=0; i<20; i++) {
                car.addSpeedPerSecond(rand.nextInt(100) + 1);
                simulateDelay();
            }

            return car;
        }, Executors.newFixedThreadPool(1));
    }

    private static void simulateDelay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}