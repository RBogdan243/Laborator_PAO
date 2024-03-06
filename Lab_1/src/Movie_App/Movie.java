package Movie_App;
import java.util.Scanner;
public class Movie {
    private String name;
    private Float rating;
    private String genre;

    public Movie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceți numele: ");
        this.name = scanner.nextLine();

        System.out.println("Introduceți ratingul: ");
        this.rating = scanner.nextFloat();
        scanner.nextLine(); // Consumă linia rămasă

        System.out.println("Introduceți genul: ");
        this.genre = scanner.nextLine();
    }

    public String getGenre() {
        return this.genre;
    }

    public Float getRating() {
        return this.rating;
    }
}