import Repository.impl.Item;
import Repository.impl.Timer;
import Service.Player;
import Service.impl.*;
import Service.GameService;

import java.sql.*;
import java.util.*;

import com.google.gson.GsonBuilder;
import org.postgresql.util.PSQLException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Escape_Room";
        Boolean auth = false;
        Scanner scanner = new Scanner(System.in);
        String input;
        Player user;
        List<Entrance> camere;
        GameService currentRoom;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Entrance.class, new EntranceAdapter())
                .create();
        while(true) {
            System.out.print("Introduceţivă numele de jucător: ");
            input = scanner.nextLine();
            try (Connection conn = DriverManager.getConnection(url, "admin", " ")) {
                PreparedStatement pstmt = conn.prepareStatement("SELECT username FROM public.\"User Authentication\" WHERE LOWER(username) = ?");
                pstmt.setString(1, input.toLowerCase());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    int j = 0;
                    while(true) {
                        System.out.print("Introduceţivă parola: ");
                        input = scanner.nextLine();
                        PreparedStatement pstmt2 = conn.prepareStatement("SELECT username FROM public.\"User Authentication\" WHERE LOWER(username) = ? AND password = ?");
                        pstmt2.setString(1, rs.getString("username").toLowerCase());
                        pstmt2.setString(2, input);
                        ResultSet ps = pstmt2.executeQuery();
                        if(!ps.next()) {
                            System.out.println("Parolă incorectă!");
                            j += 1;
                            if(j > 2) {
                                System.out.println("Ai greşit de prea multe ori, ia-o de la capăt!");
                                break;
                            }
                            System.out.print("Doriţi să reîncercaţi? y/n: ");
                            input = scanner.nextLine();
                            if(!input.toLowerCase().equals("y"))
                                break;
                        }
                        else {
                            auth = true;
                            break;
                        }
                    }
                } else {
                    String username = input;
                    System.out.print("Introduceti o parola pentru acest username: ");
                    input = scanner.nextLine();
                    PreparedStatement pstmt3 = conn.prepareStatement("INSERT INTO public.\"User Authentication\"(username, password) VALUES (?, ?)");
                    pstmt3.setString(1, username);
                    pstmt3.setString(2, input);
                    pstmt3.executeUpdate();
                    user = MyPlayer.createPlayer(username);
                    camere = MyPlayer.RandomGenerate();
                    currentRoom = camere.get(0);
                    break;
                }
                if(auth) {
                    PreparedStatement pstmt4 = conn.prepareStatement("SELECT * FROM public.\"Player\" JOIN public.\"Map\" ON public.\"Map\".username = public.\"Player\".nume WHERE LOWER(nume) = ?");
                    pstmt4.setString(1, rs.getString("username").toLowerCase());
                    ResultSet ps = pstmt4.executeQuery();
                    if(ps.next()) {
                        System.out.print("Aveţi o salvare mai veche, vreţi să continuaţi de acolo? y/n: ");
                        input = scanner.nextLine();
                        if(input.toLowerCase().equals("y")) {
                            Item[] items = gson.fromJson(ps.getString("items"), new TypeToken<Item[]>(){}.getType());
                            Timer time = gson.fromJson(ps.getString("time"), Timer.class);
                            user = MyPlayer.createPlayer(ps.getString("nume"), items, time);
                            camere = gson.fromJson(ps.getString("saved_game"), new TypeToken<List<Entrance>>(){}.getType());
                            currentRoom = gson.fromJson(ps.getString("last_state"), Entrance.class);
                        }
                        else {
                            user = MyPlayer.createPlayer(rs.getString("username"));
                            camere = MyPlayer.RandomGenerate();
                            currentRoom = camere.get(0);
                        }
                    }
                    else {
                        user = MyPlayer.createPlayer(rs.getString("username"));
                        camere = MyPlayer.RandomGenerate();
                        currentRoom = camere.get(0);
                    }
                    break;
                }
            } catch (PSQLException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("A apărut o eroare la conectarea la baza de date.");
                e.printStackTrace();
            }
        }
        System.out.println("\napăsaţi <enter> pentru a începe");
        scanner.nextLine();
        Boolean Ok = false;
        long startTime = System.nanoTime();
        while (true) {
            if (!Ok) {
                System.out.println("Eşti momentan în " + ((Entrance) currentRoom).getName() + " din casa.");
                Ok = true;
            }
            input = scanner.nextLine();
            if (input.toLowerCase().equals("close"))
                break;
            else if (input.toLowerCase().equals("look"))
                currentRoom.look();
            else if (input.toLowerCase().equals("search"))
                currentRoom.search();
            else if (input.toLowerCase().equals("check")) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                Item c = currentRoom.check(choice);
                if (!c.Nume().isEmpty() && !c.Type().isEmpty())
                    user.AddItem(c);
            } else if (currentRoom instanceof SecretRoom && input.toLowerCase().equals("safe")) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                Item c = currentRoom.safe(choice);
                if (!c.Nume().equals("") && !c.Type().equals(""))
                    user.AddItem(c);
            } else if (input.toLowerCase().equals("move")) {
                Ok = false;
                String direction = scanner.nextLine();
                if (direction.toLowerCase().equals("up") && user.CheckInventory(((Entrance) currentRoom).getNeighbors()[0]))
                    for(Entrance element : camere) {
                        if (element.getName().equals(((Entrance) currentRoom).getNeighbors()[0].Nume())) {
                            currentRoom = element;
                            break;
                        }
                    }
                else if (direction.toLowerCase().equals("left") && user.CheckInventory(((Entrance) currentRoom).getNeighbors()[1]))
                    for(Entrance element : camere) {
                        if (element.getName().equals(((Entrance) currentRoom).getNeighbors()[1].Nume())) {
                            currentRoom = element;
                            break;
                        }
                    }
                else if (direction.toLowerCase().equals("right") && user.CheckInventory(((Entrance) currentRoom).getNeighbors()[2]))
                    for(Entrance element : camere) {
                        if (element.getName().equals(((Entrance) currentRoom).getNeighbors()[2].Nume())) {
                            currentRoom = element;
                            break;
                        }
                    }
                else if (direction.toLowerCase().equals("down") && user.CheckInventory(((Entrance) currentRoom).getNeighbors()[3]))
                    for(Entrance element : camere) {
                        if (element.getName().equals(((Entrance) currentRoom).getNeighbors()[3].Nume())) {
                            currentRoom = element;
                            break;
                        }
                    }
                else if(direction.toLowerCase().equals("secret") && ((Entrance) currentRoom).getNeighbors().length == 5) {
                            System.out.print("Introdu parola pentru a intra: ");
                            int pass = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println();
                            if(pass == ((Entrance) currentRoom).getNeighbors()[4].Password())
                                for(Entrance element : camere) {
                                    if (element.getName().equals(((Entrance) currentRoom).getNeighbors()[4].Nume())) {
                                        currentRoom = element;
                                        break;
                                    }
                                }
                            else {
                                System.out.println("Parolă incorectă!");
                                Ok = true;
                            }
                        }
                else {
                    boolean Ok1 = false;
                    for(int i = 0; i < ((Entrance) currentRoom).getNeighbors().length; i++)
                        if(((Entrance) currentRoom).getNeighbors()[i].Nume().equals("Intrare")) {
                            currentRoom = camere.getFirst();
                            Ok1 = true;
                            break;
                        }
                    if(!Ok1) {
                        Ok = true;
                        System.out.println("Nu poţi să mergi în acea directie.");
                    }
                }
            } else if (input.toLowerCase().equals("inventory"))
                user.ShowInventory();
            else if (currentRoom instanceof FinalRoom && input.toLowerCase().equals("exit")) {
                String put = user.getKey();
                if(currentRoom.exit(put))
                    break;
                else System.out.println("Cheia selectată nu se potriveşte!");
            } else System.out.println("Comanda inexistenta.");
            user.TimerSteps();
        }
        long endTime = System.nanoTime();
        user.TimerGame((endTime - startTime) / 1000000000 + ((MyPlayer) user).getTime().timeSec());
        try (Connection conn = DriverManager.getConnection(url, "admin", " ");
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM public.\"Player\" JOIN public.\"Map\" ON public.\"Map\".username = public.\"Player\".nume WHERE LOWER(nume) = ?")) {
            pstmt.setString(1, ((MyPlayer) user).getNume().toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                PreparedStatement pstmt1 = conn.prepareStatement("DELETE FROM public.\"Player\" WHERE LOWER(nume) = ?;");
                pstmt1.setString(1, ((MyPlayer) user).getNume().toLowerCase());
                pstmt1.executeUpdate();
                PreparedStatement pstmt2 = conn.prepareStatement("DELETE FROM public.\"Map\" WHERE LOWER(username) = ?;");
                pstmt2.setString(1, ((MyPlayer) user).getNume().toLowerCase());
                pstmt2.executeUpdate();
            }
        } catch (PSQLException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("A apărut o eroare la conectarea la baza de date.");
            e.printStackTrace();
        }
        if (!input.toLowerCase().equals("close")) {
            System.out.println("Bravo, ai scăpat din casă!");
        }
        System.out.println("Play Time: " + ((MyPlayer) user).getTime().timeSec() / 60 + " minute şi " + (((MyPlayer) user).getTime().timeSec() - (((MyPlayer) user).getTime().timeSec() / 60) * 60) + " secunde.");
        System.out.println("Număr de comenzi rulate: " + ((MyPlayer) user).getTime().stepsTaken());
        if(!input.toLowerCase().equals("close")) {
            long check = (540000 - ((MyPlayer) user).getTime().stepsTaken() * ((MyPlayer) user).getTime().timeSec());
            String lastDigits = "" + check;
            lastDigits = "" + lastDigits.charAt(3) + lastDigits.charAt(4) + lastDigits.charAt(5);
            System.out.println("Scor final: " + (540000 - ((MyPlayer) user).getTime().stepsTaken() * ((MyPlayer) user).getTime().timeSec()) / 1000 + " " + lastDigits);
            try (Connection conn = DriverManager.getConnection(url, "admin", " ");
                 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM public.\"Leaderboard\" WHERE LOWER(\"Username\") = ? AND \"Score\" <= ?;")) {
                pstmt.setString(1, ((MyPlayer) user).getNume().toLowerCase());
                pstmt.setInt(2, ((int) check));
                ResultSet ps = pstmt.executeQuery();
                if(ps.next()) {
                    PreparedStatement pstmt1 = conn.prepareStatement("UPDATE public.\"Leaderboard\" SET \"Score\" = ?, \"Escape Time\" = ? WHERE \"Username\" = ?;");
                    pstmt1.setInt(1, ((int) check));
                    pstmt1.setString(2, ((MyPlayer) user).getTime().timeSec() / 60 + " minute şi " + (((MyPlayer) user).getTime().timeSec() - (((MyPlayer) user).getTime().timeSec() / 60) * 60) + " secunde");
                    pstmt1.setString(3, ((MyPlayer) user).getNume());
                    pstmt1.executeUpdate();
                }
                else {
                    PreparedStatement pstmt1 = conn.prepareStatement("SELECT * FROM public.\"Leaderboard\" WHERE LOWER(\"Username\") = ?;");
                    pstmt1.setString(1, ((MyPlayer) user).getNume().toLowerCase());
                    ResultSet rs = pstmt1.executeQuery();
                    if(!rs.next()) {
                        PreparedStatement pstmt2 = conn.prepareStatement("INSERT INTO public.\"Leaderboard\"(\"Username\", \"Score\", \"Escape Time\") VALUES (?, ?, ?);");
                        pstmt2.setString(1, ((MyPlayer) user).getNume());
                        pstmt2.setInt(2, ((int) check));
                        pstmt2.setString(3, ((MyPlayer) user).getTime().timeSec() / 60 + " minute şi " + (((MyPlayer) user).getTime().timeSec() - (((MyPlayer) user).getTime().timeSec() / 60) * 60) + " secunde");
                        pstmt2.executeUpdate();
                    }
                }
                System.out.print("Doriţi să vedeţi Leaderboard-ul? y/n: ");
                input = scanner.nextLine();
                if(input.toLowerCase().equals("y")) {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM public.\"Leaderboard\" ORDER BY \"Score\" DESC;");
                    System.out.println("Leaderboard:");
                    int i = 1;
                    while(rs.next()) {
                        System.out.println(i + ". " + rs.getString("Username") + " (score: " + rs.getString("Score") + " în " + rs.getString("Escape Time") + ")");
                        i += 1;
                    }
                }
            } catch (PSQLException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("A apărut o eroare la conectarea la baza de date.");
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Scor final: 0");
            try (Connection conn = DriverManager.getConnection(url, "admin", " ");
                 PreparedStatement pstmt = conn.prepareStatement("INSERT INTO public.\"Player\"(nume, \"time\", items) VALUES (?, ?, ?);")) {
                pstmt.setString(1, ((MyPlayer) user).getNume());
                pstmt.setString(2, gson.toJson(((MyPlayer) user).getTime()));
                pstmt.setString(3, gson.toJson(((MyPlayer) user).getItems()));
                pstmt.executeUpdate();
                PreparedStatement pstmt1 = conn.prepareStatement("INSERT INTO public.\"Map\"(username, saved_game, last_state) VALUES (?, ?, ?);");
                pstmt1.setString(1, ((MyPlayer) user).getNume());
                pstmt1.setString(2, gson.toJson(camere));
                pstmt1.setString(3, gson.toJson(currentRoom));
                pstmt1.executeUpdate();
            } catch (PSQLException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("A apărut o eroare la conectarea la baza de date.");
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}