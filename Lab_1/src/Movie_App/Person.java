package Movie_App;
import java.util.Scanner;

public class Person {
    private String name;
    private String favoriteGenre;
    private Float minRating;

    public Person() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceți numele: ");
        this.name = scanner.nextLine();

        System.out.println("Introduceți genul favorit: ");
        this.favoriteGenre = scanner.nextLine();

        System.out.println("Introduceți ratingul minim: ");
        this.minRating = scanner.nextFloat();
    }

    public Boolean chooseMovie(Movie[] movies) {
        for (Movie movie : movies) {
            if (movie.getGenre().equals(this.favoriteGenre) && movie.getRating() >= this.minRating) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
