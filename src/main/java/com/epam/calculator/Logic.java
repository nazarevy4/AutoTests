package com.epam.calculator;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Logic {
    double firstNumber;
    double secondNumber;
    char action;
    double result;
    boolean runAgain;
    int countOfaction;
    String currentDate;

    public void startProgram () throws IOException {
        do
        {
            if (countOfaction == 0) {
                recieveFirstNumber();
            } else {
                firstNumber = result;
            }
            recieveAction();
            recieveSecondNumber();
            calcLogic();
            nextRun();
            writeResult2();
        } while (runAgain);
    }

    public void calcLogic () {
        switch (action){
            case '+': result = addition(firstNumber,secondNumber);break;
            case '-': result = subtraction(firstNumber,secondNumber);break;
            case '*': result = multiplication(firstNumber,secondNumber);break;
            case '/': result = division(firstNumber,secondNumber);break;
        }
    }

    public void nextRun () {
        System.out.println("the result is " + result);
        countOfaction++;
        askForRun();
    }

    public void askForRun () {
        Scanner sc = new Scanner(System.in);
        System.out.println("next Action? Y/N");
        char answer = sc.next().charAt(0);
        switch (answer) {
            case 'Y':runAgain = true;break;
            case 'N':runAgain = false;break;
        }
    }

    public void recieveFirstNumber () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter first number");
        double temp = sc.nextDouble();
        if (temp > -20.0&&temp < 20) {
            firstNumber = temp;
        }else {
            System.out.println("incorrect type");
            recieveFirstNumber();
        }
    }

    public void recieveSecondNumber () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter second number");
        double temp = sc.nextDouble();
        if (temp > -20.0&&temp < 20) {
            secondNumber = temp;
        }else {
            System.out.println("incorrect type");
            recieveSecondNumber();
        }
    }

    public void recieveAction () {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter action");
        char temp = sc.next().charAt(0);
        if (temp == '*'||temp == '/'||temp == '+'||temp == '-'){
            action = temp;
        }else{
            System.out.println("ono");
            recieveAction();
        }
    }

    public double addition (double firstNumber, double secondNumber) {
        double result = firstNumber + secondNumber;
        return result;
    }

    public double subtraction (double firstNumber, double secondNumber) {
        double result = firstNumber - secondNumber;
        return result;
    }

    public double multiplication (double firstNumber, double secondNumber) {
        double result = firstNumber * secondNumber;
        return result;
    }

    public double division (double firstNumber, double secondNumber) {
        double result = firstNumber / secondNumber;
        return result;
    }

    public void writeResult () throws FileNotFoundException {
        PrintWriter out = new PrintWriter("c:/Nasdaq/temp/temp.txt");
        currentDate = extractCurrentDate();
        out.println(result + " was calculated on " + currentDate);
        out.close();
    }

    public void writeResult2 () throws IOException {
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