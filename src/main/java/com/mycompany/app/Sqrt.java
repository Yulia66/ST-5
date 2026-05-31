package com.mycompany.app;

class SquareRootCalculator
{
    private double precision = 0.00000001;
    private double targetValue;

    public SquareRootCalculator(double targetValue) {
        this.targetValue = targetValue;
    }

    public double arithmeticMean(double first, double second) {
        return (first + second) / 2.0;
    }

    public boolean isAcceptable(double estimate, double value) {
        return Math.abs(estimate * estimate - value) < precision;
    }

    public double refineEstimate(double estimate, double value) {
        return arithmeticMean(estimate, value / estimate);
    }

    public double iterate(double estimate, double value) {
        if (isAcceptable(estimate, value))
            return estimate;
        else
            return iterate(refineEstimate(estimate, value), value);
    }

    public double compute() {
        return iterate(1.0, targetValue);
    }
    
    public void setPrecision(double newPrecision) {
        this.precision = newPrecision;
    }
    
    public double getPrecision() {
        return precision;
    }
}
