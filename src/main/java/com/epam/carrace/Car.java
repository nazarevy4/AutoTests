package com.epam.carrace;

import java.util.ArrayList;
import java.util.List;

public class Car {

    private List<Integer> speedPerSecond = new ArrayList<Integer>();
    private int carNumber;
    private List<Integer> positionPerSecond = new ArrayList<>();
    private int averageSpeed;

    public void setAverageSpeed (){
        int sum = 0;
        for (Integer sps: speedPerSecond){
            sum += sps;
        }
        this.averageSpeed = sum/speedPerSecond.size();
    }


    public List getSpeedPerSecond() {
        return speedPerSecond;
    }

    public void setSpeedPerSecond(List speedPerSecond) {
        this.speedPerSecond = speedPerSecond;
    }

    public List getPositionPerSeconds (){
        return positionPerSecond;
    }

    public void addPositionPerSeconds(int positionPerSeconds) {this.positionPerSecond.add(positionPerSeconds);}

    public void addSpeedPerSecond(int speedPerSecond) {
        this.speedPerSecond.add(speedPerSecond);
    }

    public double getAvarageSpeed(){
        int sum = 0;
        for (Integer sps: speedPerSecond){
            sum += sps;
        }
        return sum/speedPerSecond.size();
    }


    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }
}