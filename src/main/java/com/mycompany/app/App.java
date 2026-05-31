package com.mycompany.app;

public class MainApp
{
    public static void main(String[] args)
    {
        double number = Double.parseDouble("2.0");
        SquareRootCalculator calculator = new SquareRootCalculator(number);
        double rootValue = calculator.compute();
        System.out.println("Square root of " + number + " = " + rootValue);
    }
}
