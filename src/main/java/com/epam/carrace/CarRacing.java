package com.epam.carrace;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class CarRacing {


    public static void main(String[] args) {
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
        finalResult.forEach(t -> System.out.println("Finished Car " + t.getCarNumber() + " with average speed " + t.getAvarageSpeed()));
        finalResult = getPositionsPerEachSecond(finalResult, timeOfRaceSeconds);
        finalResult = sortCarsBySpeed(finalResult);
        printFirstPlaces(3, finalResult);

    }

    private static List<Car> getPositionsPerEachSecond (List<Car> listWithCars, int timeOfRace){
        Map<Integer, Integer> timePerSecond = new HashMap<>();
        Map<Integer, Integer> overallTime = new HashMap<>();
        Map<Integer, Integer> sortedOverallTime;
        for (int i = 0; i < timeOfRace; i++) {
            int numberOfSecond = i;
            listWithCars.forEach(t -> timePerSecond.put(t.getCarNumber(), Integer.valueOf((Integer) t.getSpeedPerSecond().get(numberOfSecond))));
            timePerSecond.forEach((k,v)->overallTime.merge(k,v, Integer::sum));
            sortedOverallTime = mapSoring(overallTime);
            List<Integer> testList= new ArrayList<>(sortedOverallTime.keySet());
            Collections.reverse(testList);
            listWithCars.forEach(t -> t.addPositionPerSeconds(1+testList.indexOf(t.getCarNumber())));
        }return listWithCars;
    }

    private static List<Car> sortCarsBySpeed (List<Car> listToSort) {
        listToSort.sort(Comparator.comparing(Car::getAvarageSpeed).reversed());
        return listToSort;
    }

    private static void printFirstPlaces (int numberOfPlaces, List<Car> testList) {
        for (int i = 0; i<numberOfPlaces; i++) {
            int place = i+1;
            System.out.println("car on the " + place + " place with number " + testList.get(i).getCarNumber() + " with positions per seconds " + testList.get(i).getPositionPerSeconds() + " and speeds per seconds " + testList.get(i).getSpeedPerSecond());
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
        CompletableFuture<Car> completableFuture = CompletableFuture.supplyAsync(() -> {
            Random rand = new Random();
            Car car = new Car();
            car.setCarNumber(carNumber);
            for(int i=0; i<20; i++) {
                car.addSpeedPerSecond(rand.nextInt(200) + 1);
                simulateDelay();
            }
            return car;
        }, Executors.newFixedThreadPool(1));
        return completableFuture;
    }

    private static void simulateDelay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}