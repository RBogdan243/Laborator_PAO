import logare.LogLevel;
import service.Logger;
import service.impl.Book;
import service.impl.MyLogger;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Book> carti = new ArrayList<>();
        int n = scanner.nextInt();
        scanner.nextLine();
        for(int i = 0; i < n; i++) {
            carti.add(new Book());
        }
        Collections.sort(carti, Comparator.comparing(Book::getRating).reversed());
        Logger log = MyLogger.getInstance();
        for(int i = 0; i < n; i++) {
            log.logInfo("cartea '" + carti.get(i).getNume() + "' a fost adaugată");
        }
        Set<Book> setCarti = new HashSet<>();
        n = scanner.nextInt();
        scanner.nextLine();
        List<String> check = new ArrayList<>();

        int i = 0;
        while (i < n) {
            Book a = new Book();
            if(!check.contains(a.getNume().toLowerCase())) {
                setCarti.add(a);
                log.logInfo("cartea '" + a.getNume() + "' a fost adaugată");
                check.add(a.getNume().toLowerCase());
                i ++;
            }
            else log.log(LogLevel.WARN, "cartea '" + a.getNume() + "' exita deja în baza de date");
        }
        scanner.close();
    }
}