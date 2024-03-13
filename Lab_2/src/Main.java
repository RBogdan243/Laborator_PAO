import AnimalApp.Animal;
import AnimalApp.Cat;
import AnimalApp.Dog;
import AnimalApp.Snake;

import java.util.Scanner;

public class Main {
    public static void main(String[]args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceti cate animale doriti sa adaugati:");
        int n = scanner.nextInt();
        scanner.nextLine();
        Animal[] array = new Animal[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Ce animal doriti?");
            String animal = scanner.nextLine();
            if(animal.toLowerCase().equals("caine")) {
                array[i] = new Dog();
            }
            else if(animal.toLowerCase().equals("pisica")) {
                array[i] = new Cat();
            }
            else {
                array[i] = new Snake();
            }
        }

        for (int i = 0; i < n; i++) {
            array[i].Print();
            array[i].talk();
            array[i].walk();
        }

        scanner.close();
        }
}