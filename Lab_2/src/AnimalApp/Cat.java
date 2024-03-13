package AnimalApp;
import java.util.Scanner;

public class Cat extends Animal {
    private String jucaria_preferata;

    public Cat() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceți jucaria lui/ei preferata: ");
        this.jucaria_preferata = scanner.nextLine();
    }
    @Override
    public void talk() {
        System.out.println("MEOOOOW");
    }

    @Override
    public void Print() {
        System.out.println("Animalul selectat este o pisica.");
        System.out.println("Ea are numele: " + this.getNume());
        System.out.println("Ea are: " + this.getVarsta() + " Ani.");
        System.out.println("Ea are ca jucarie preferata: " + this.jucaria_preferata);
    }
}
