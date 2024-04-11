package ServicePC.impl;

import ServicePC.Calculator;
import ServicePC.exception.Exceptie;

public class MyCalculator implements Calculator {
    @Override
    public double add(Double a, Double b) {
        double sum;
        try {
            Exceptie.Add(a, b);
            sum = a + b;
        } catch (Exception e) {
            Exceptie.logException(e);
            System.out.println(e.getMessage());
            return 0;
        }
        return sum;
    }

    @Override
    public double divide(Double a, Double b) {
        double div;
        try {
            Exceptie.Divide(a, b);
            div = a / b;
        } catch (Exception e) {
            Exceptie.logException(e);
            System.out.println(e.getMessage());
            return 0;
        }
        return div;
    }

    @Override
    public double average(Double[] a) {
        double average;
        try {
            Exceptie.Average(a);
            double sum = 0;
            for(int i = 1; i < a.length; i += 2)
                sum += this.add(a[i-1], a[i]);
            double n = a.length;
            average = this.divide(sum, n);
        } catch (Exception e) {
            Exceptie.logException(e);
            System.out.println(e.getMessage());
            return 0;
        }
        return average;
    }
}
