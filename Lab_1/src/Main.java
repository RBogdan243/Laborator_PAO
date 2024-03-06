import Movie_App.Movie;
import Movie_App.Person;

import java.util.Scanner;

public class Main {
    public static void main(String[]args) {

        System.out.println("Hello PAO!");

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nIntrodu nr. de filme: ");
        int n = scanner.nextInt();
        if(n < 4) {n = 4;}
        Movie[] movie = new Movie[n];

        for(int i=0; i<n; i++) {
            System.out.print("Filmul " + (i+1) + '\n');
            movie[i] = new Movie();
            System.out.print('\n');
        }

        System.out.print("\nIntrodu persoana:\n");
        Person om = new Person();

        scanner.close();

        if(om.chooseMovie(movie)) {
            System.out.println("Da!");
        }
        else System.out.println("Nu!");
    }
}