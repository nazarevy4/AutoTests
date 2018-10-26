package com.epam.calcnew;

public class ActionFactory {

    public static CalcAction createAction(char action)
    {
        CalcAction calcAction;
        switch (action) {
            case '+': calcAction = new Addition();break;
            case '-': calcAction = new Subtraction();break;
            case '/': calcAction = new Division();break;
            case '*': calcAction = new Multiplication();break;
            default: throw new UnsupportedOperationException();
        }return calcAction;
    }
}
