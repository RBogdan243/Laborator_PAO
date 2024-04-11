package ServicePC.exception;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Exceptie {
    public static void Add(Double a, Double b) throws Exception {
        if (a == null || b == null)
            throw new NullParameterException("NullParameterException!\nUn parametru sau mai multi sunt nuli!");
        else if(a + b == Double.POSITIVE_INFINITY)
            throw new OverflowException("OverflowException!\nUn element selectat a trecut peste limita superioara a lui Double!");
        else if(a + b == Double.NEGATIVE_INFINITY)
            throw new UnderflowException("UnderflowException!\nUn element selectat a trecut peste limita inferioara a lui Double!");
    }

    public static void Divide(Double a, Double b) throws Exception {
        if(a == null || b == null)
            throw new NullParameterException("NullParameterException!\nUn parametru sau mai multi sunt nuli!");
        else if(a / b == Double.POSITIVE_INFINITY)
            throw new OverflowException("OverflowException!\nUn element selectat a trecut peste limita superioara a lui Double!");
        else if(a / b == Double.NEGATIVE_INFINITY)
            throw new UnderflowException("UnderflowException!\nUn element selectat a trecut peste limita inferioara a lui Double!");
    }

    public static void Average(Double[] a) throws Exception {
        if(a == null)
            throw new NullParameterException("NullParameterException!\nLista data este nula!");
        else if(a.length == 0)
            throw new EmptyStackException("EmptyStackException!\nLista data este goala!");
    }

    public static void logException(Exception e) {
        try (FileWriter fw = new FileWriter("exceptions.log", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(e.getMessage());
        } catch (IOException ioe) {
            System.out.println("Eroare la scrierea în fișier: " + ioe.getMessage());
        }
    }
}
