package com.epam.calcnew;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CalcImplementation {
    double firstNumber;
    double secondNumber;
    char action;
    double result;
    boolean runAgain;
    int countOfaction;
    String currentDate;

    public void calcActions () {
        switch (action){
            case '+': ActionFactory addition = new ActionFactory(new Addition());
                        result = addition.execute(firstNumber, secondNumber);break;
            case '-': ActionFactory subtraction = new ActionFactory(new Subtraction());
                        result = subtraction.execute(firstNumber, secondNumber);break;
            case '/': ActionFactory division = new ActionFactory(new Division());
                        result = division.execute(firstNumber, secondNumber);break;
            case '*':ActionFactory multiplication = new ActionFactory(new Multiplication());
                        result = multiplication.execute(firstNumber, secondNumber);
        }
    }

    public void runCalculator () throws IOException {
        do
        {
            if (countOfaction == 0) {
               firstNumber = getNumberFromClient("first");
            } else {
                firstNumber = result;
            }
            getActionfromClient();
            secondNumber = getNumberFromClient("second");
            calcActions();
            nextAction();
            writeResult();
        } while (runAgain);
    }

    public void nextAction () {
        System.out.println("the result is " + result);
        countOfaction++;
        askForNextAction();
    }

    public void askForNextAction () {
        Scanner sc = new Scanner(System.in);
        System.out.println("next Action? Y/N");
        char answer = sc.next().charAt(0);
        switch (answer) {
            case 'Y':runAgain = true;break;
            case 'N':runAgain = false;break;
        }
    }

    public double getNumberFromClient (String countOfnumber){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter " + countOfnumber + " number");
        double tempNumber;
        do {
            while (!scanner.hasNextDouble()) {
                scanner.next();
                System.out.println("not a double, try again");
            }
            tempNumber = scanner.nextDouble();
            if (tempNumber < -20.0 || tempNumber > 20) {
                System.out.println("number is out of range, try again");
            }
        } while (tempNumber < -20.0 || tempNumber > 20);
        return tempNumber;
    }

    public void getActionfromClient () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter action");
        do {
            action = scanner.next().charAt(0);
            if (action != '*' && action != '/' && action != '+' && action != '-') {
                System.out.println("number is out of range, try again");
            }
        }
        while (action != '*'&&action != '/' && action != '+'&& action != '-');
        }


    public void writeResult () throws IOException {
        currentDate = extractCurrentDate();
        Writer output = new BufferedWriter(new FileWriter("c:/Nasdaq/temp/temp.txt", true));
        output.write(result + " was calculated on " + currentDate);
        output.close();
    }

    public String extractCurrentDate () {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
