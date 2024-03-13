package AnimalApp;
import java.util.Scanner;

public class Animal {
    private String nume;
    private Integer varsta;

    public Animal() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceți numele: ");
        this.nume = scanner.nextLine();

        System.out.println("Introduceți varsta: ");
        this.varsta = scanner.nextInt();
        scanner.nextLine();
    }

    public void talk() { System.out.println("Default."); }
    public void walk() { System.out.println("Default."); }

    public void Print() {}

    public String getNume() {
        return this.nume;
    }

    public Integer getVarsta() {
        return this.varsta;
    }
}
