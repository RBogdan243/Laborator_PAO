package AnimalApp;
import java.util.Scanner;

public class Dog extends Animal {
    private String mancarea_preferata;

    public Dog() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceți mancarea lui/ei preferata: ");
        this.mancarea_preferata = scanner.nextLine();
    }

    @Override
    public void talk() {
        System.out.println("WOOOOOF");
    }

    @Override
    public void walk() {
        System.out.println("RUUUUUUUUUN");
    }

    @Override
    public void Print() {
        System.out.println("Animalul selectat este un caine.");
        System.out.println("El are numele: " + this.getNume());
        System.out.println("El are: " + this.getVarsta() + " Ani.");
        System.out.println("El are ca mancare preferata: " + this.mancarea_preferata);
    }
}
