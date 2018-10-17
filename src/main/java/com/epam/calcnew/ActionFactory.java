package com.epam.calcnew;

public class ActionFactory {
    private CalcAction calcAction;

    public ActionFactory (CalcAction strategy){
        this.calcAction = strategy;
    }

    public double execute (double num1, double num2){
        return calcAction.performOperation(num1, num2);
    }

}
