import java.sql.*;

import model.Employee;
import org.postgresql.util.PSQLException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Lab_6";
        String user = "postgres";
        System.out.print("Introdu parola: ");
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        Main.executeAndPrintQuery("SELECT * FROM public.\"Employees\";", url, user, password);
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM public.\"Employees\";")) {

            int rowsAffected = stmt.executeUpdate("UPDATE public.\"Employees\" SET nume = 'Becali' WHERE id = 2;");
            System.out.println("\nNumărul de rânduri afectate: " + rowsAffected + '\n');
            Main.executeAndPrintQuery("SELECT * FROM public.\"Employees\";", url, user, password);

        } catch (PSQLException e) {
            System.out.println("Parolă incorectă!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("A apărut o eroare la conectarea la baza de date.");
            e.printStackTrace();
        }
    }

    public static void executeAndPrintQuery(String query, String url, String user, String password) {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employee = mapRowToEmployee(rs);
                System.out.println(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Employee mapRowToEmployee(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nume = rs.getString("nume");
        int experience_days = rs.getInt("experience_days");
        return new Employee(id, nume, experience_days);
    }
}