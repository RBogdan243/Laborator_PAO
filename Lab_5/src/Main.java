import ServicePC.Calculator;
import ServicePC.impl.MyCalculator;

public class Main {
    public static void main(String[] args) {
        Calculator PC = new MyCalculator();
        PC.add(Double.POSITIVE_INFINITY, 1.57);
        PC.divide(Double.NEGATIVE_INFINITY, 57.99);
        PC.average(null);
        PC.average(new Double[]{1.0, null, 1.7});
        PC.average(new Double[]{});
        System.out.println("Media: " + PC.average(new Double[]{1.7, 2.9, 3.7, 4.5}));
    }
}