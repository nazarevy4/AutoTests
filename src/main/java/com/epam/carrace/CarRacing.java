package com.epam.carrace;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CarRacing {


    public static void main(String[] args) {
//        test
        final int numberOfCars = 10;
        final int timeOfRaceSeconds = 20;

        List<CompletableFuture<Car>> tasks = new ArrayList<CompletableFuture<Car>>();

        for (int i = 1; i <=numberOfCars; i++) {
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

        List<Car> finalResult = (List<Car>) completableFuture.join();
        finalResult.forEach(t->t.setAverageSpeed());
        finalResult.forEach(t -> System.out.println("Finished Car " + t.getCarNumber() + " with avarge speed " + t.getAvarageSpeed()));
        Map<Integer, Integer> timePerSecond = new HashMap<>();
        Map<Integer, Integer> overallTime = new HashMap<>();
        Map<Integer, Integer> sortedOverallTime;
        for (int i = 0; i < timeOfRaceSeconds; i++) {
            int numberOfSecond = i;
            finalResult.forEach(t -> timePerSecond.put(t.getCarNumber(), Integer.valueOf((Integer) t.getSpeedPerSecond().get(numberOfSecond))));
            timePerSecond.forEach((k,v)->overallTime.merge(k,v, Integer::sum));
            sortedOverallTime = mapSoring(overallTime);
            List<Integer> testList;
            testList = new ArrayList<>(sortedOverallTime.keySet());
            Collections.reverse(testList);
            finalResult.forEach(t -> t.addPositionPerSeconds(1+testList.indexOf(t.getCarNumber())));
        }
            finalResult.sort(Comparator.comparing(Car::getAvarageSpeed));
            int temp = 3;
            for (int i=numberOfCars-3;i<numberOfCars;i++){
                System.out.println("car on the " + temp + " place with number " + finalResult.get(i).getCarNumber() + " with positions per seconds " + finalResult.get(i).getPositionPerSeconds() + " and speeds per seconds " + finalResult.get(i).getSpeedPerSecond());
                temp-=1;
            }


    }

    private static HashMap<Integer, Integer> mapSoring (Map<Integer, Integer> testMap){
        HashMap<Integer, Integer> sortedmap = testMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return sortedmap;
    }


    private static CompletableFuture<Car> createRace(final int carNumber){
        return CompletableFuture.supplyAsync(() -> {

            Random rand = new Random();

            Car car = new Car();
            car.setCarNumber(carNumber);
            for(int i=0; i<20; i++) {
                car.addSpeedPerSecond(rand.nextInt(200) + 1);
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