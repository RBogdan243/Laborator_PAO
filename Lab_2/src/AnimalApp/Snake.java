package AnimalApp;

import java.util.Scanner;

public class Snake extends Animal {
    private Boolean Venomenos;

    public Snake() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceți daca are venom sau nu: ");
        String rasp =scanner.nextLine();
        if(rasp.toLowerCase().equals("da")) {
            this.Venomenos = Boolean.TRUE;
        }
    }

    @Override
    public void Print() {
        System.out.println("Animalul selectat este un sarpe.");
        System.out.println("El are numele: " + this.getNume());
        System.out.println("El are: " + this.getVarsta() + " Ani.");
        if(this.Venomenos) {
            System.out.println("El este periculos.");
        }
    }
}
