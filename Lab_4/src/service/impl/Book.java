package service.impl;

import java.util.Scanner;

public class Book {
    private String Nume;
    private String Descriere;
    private Double Rating;

    public Book() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceţi numele cărţii: ");
        this.Nume = scanner.nextLine();
        System.out.print('\n');
        System.out.print("Introduceţi descrierea cărţii: ");
        this.Descriere = scanner.nextLine();
        System.out.print('\n');
        System.out.print("Introduceţi rating-ul cărţii: ");
        this.Rating = scanner.nextDouble();
        scanner.nextLine();
        System.out.print('\n');
    }

    public Double getRating() {
        return this.Rating;
    }

    public String getNume() {
        return this.Nume;
    }
}
