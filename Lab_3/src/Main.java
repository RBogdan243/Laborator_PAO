import model.Movie;
import service.MovieService;
import service.impl.MovieServiceImpl;
import service.impl.repo.repository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MovieService serviciu = new MovieServiceImpl(new repository());
        Scanner scan = new Scanner(System.in);

        for(int i = 0; i < 3; i++) {
            System.out.println("Introdu filmul: ");
            String film = scan.nextLine();
            serviciu.AddFilm(film);
        }

        System.out.println("Introdu numele filmului dorit: ");
        String film = scan.nextLine();
        serviciu.SearchFilm(film);

        System.out.println("Introdu numele partial al filmului dorit: ");
        film = scan.nextLine();
        serviciu.SearchFilm(film);

        System.out.println("Introdu numele unui filmului neexistent: ");
        film = scan.nextLine();
        serviciu.SearchFilm(film);

        System.out.println("Introdu numele unui filmului pe care vreti sa-l stergeti: ");
        film = scan.nextLine();
        serviciu.DeleteFilm(film);
        serviciu.SearchFilm(film);

        System.out.println("Introdu numele unui filmului pe care vreti sa-l schimbati: ");
        film = scan.nextLine();
        System.out.println("Introdu numele filmului cu care vreti sa-l schimbati: ");
        String film1 = scan.nextLine();
        serviciu.ChangeFilm(film, film1);
        serviciu.SearchFilm(film1);

        scan.close();
    }
}