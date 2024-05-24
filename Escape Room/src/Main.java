import Repository.AuditService;
import Repository.DatabaseService;
import Repository.impl.Item;
import Repository.impl.MyDatabase;
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
        DatabaseService dbService = MyDatabase.getInstance();
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
            try {
                ResultSet rs = dbService.select("SELECT username FROM public.\"User Authentication\" WHERE LOWER(username) = ?", input.toLowerCase());
                if (rs.next()) {
                    int j = 0;
                    while(true) {
                        System.out.print("Introduceţivă parola: ");
                        input = scanner.nextLine();
                        ResultSet ps = dbService.select("SELECT username FROM public.\"User Authentication\" WHERE LOWER(username) = ? AND password = ?", rs.getString("username").toLowerCase(), input);
                        if(!ps.next()) {
                            System.out.println("Parolă incorectă!");
                            j += 1;
                            if(j > 2) {
                                AuditService.getInstance().logAction("Failed Authentication");
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
                    if(dbService.insert("INSERT INTO public.\"User Authentication\"(username, password) VALUES (?, ?)", username, input) == 0) {
                        System.err.println("Nu s-a putut insera nimic în baza de date!");
                        System.exit(0);
                    }
                    AuditService.getInstance().logAction("Register");
                    user = MyPlayer.createPlayer(username);
                    camere = MyPlayer.RandomGenerate();
                    currentRoom = camere.get(0);
                    break;
                }
                if(auth) {
                    AuditService.getInstance().logAction("Login");
                    ResultSet ps = dbService.select("SELECT * FROM public.\"Player\" JOIN public.\"Map\" ON public.\"Map\".username = public.\"Player\".nume WHERE LOWER(nume) = ?", rs.getString("username").toLowerCase());
                    if(ps.next()) {
                        System.out.print("Aveţi o salvare mai veche, vreţi să continuaţi de acolo? y/n: ");
                        input = scanner.nextLine();
                        if(input.toLowerCase().equals("y")) {
                            AuditService.getInstance().logAction("Load Save");
                            Item[] items = gson.fromJson(ps.getString("items"), new TypeToken<Item[]>(){}.getType());
                            Timer time = gson.fromJson(ps.getString("time"), Timer.class);
                            user = MyPlayer.createPlayer(ps.getString("nume"), items, time);
                            camere = gson.fromJson(ps.getString("saved_game"), new TypeToken<List<Entrance>>(){}.getType());
                            currentRoom = gson.fromJson(ps.getString("last_state"), Entrance.class);
                        }
                        else {
                            AuditService.getInstance().logAction("New Game");
                            user = MyPlayer.createPlayer(rs.getString("username"));
                            camere = MyPlayer.RandomGenerate();
                            currentRoom = camere.get(0);
                        }
                    }
                    else {
                        AuditService.getInstance().logAction("New Game");
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
        System.out.println("Daca ai nevoie de ajutor foloseşte comanda \"help\"");
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
            else if (input.toLowerCase().equals("help"))
                System.out.println("Scopul tău în acest joc este să scapi din casă,\nPoti face asta doar dacă găseşti toate cele 8 chei şi apelând comada \"Exit\" din camera \"Ieşire\",\nO să găseşti pe lângă chei mai multe scrisori şi un bon de cumpărături care o să te ajute în a dezvălui toate secretele casei!\nMult succes!\nListă comenzi:\nSearch: această comadă îţi arată toate obiectele care pot fi căutate\nCheck: această comadă te lasă să cauţi prin obiecte prin a selecta unul după numărul returnat de comada \"Search\" şi îţi zice dacă ai găsit ceva sau nu\nInventory: această comadă te lasă să îţi verifici inventarul şi să inspectezi mai în detaliu obiectele prin comada \"Inpsect\" urmata de numărul asociat lui\nLook: această comadă îţi arată toate camerele din jurul tău\nMove: această comadă te lasă să decizi în ce direcţie vrei să mergi, aici ai mai multe obţiuni:\n    1.\"Up\" pentru a te îndrepta în sus\n    2.\"Down\" pentru a te îndrepta în jos\n    3.\"Left\" pentru a te îndrepta în stânga\n    4.\"Right\" pentru a te îndrepta în dreapta\n    5.\"Secret\" pentru a accesa o cameră secretă (această comadă este una specială, şi nu merge în orice cameră)\nExit: această comadă te lasă să selectezi un obiect cu care vei încerca să deschizi ultima uşă şi să scapi (această comadă este una specială şi merge numai în camera \"Ieşire\" şi necesită obiectul \"FinalKey\" pentru a funcţiona)\nHelp: această comadă îţi afişează date utile despre scopul jocului şi toate comenzile existente\nSafe: această comadă te lasă să introduci un cod de 3 cifre pentru a deschide un seif (această comadă este una specială şi merge numai într-o cameră secretă)\nClose: această comadă te lasă să părăseşti jocul (jocul va fi salvat până la crearea unuia nou \"New Game\", poţi oricând să te întorci de unde ai rămas când te autentifici atâta timp cât nu ai optat pentru \"New Game\")");
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
                else System.out.println("Nu poţi să mergi în acea directie.");
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
        try {
            ResultSet rs = dbService.select("SELECT * FROM public.\"Player\" JOIN public.\"Map\" ON public.\"Map\".username = public.\"Player\".nume WHERE LOWER(nume) = ?", ((MyPlayer) user).getNume().toLowerCase());
            if(rs.next()) {
                if(dbService.delete("DELETE FROM public.\"Player\" WHERE LOWER(nume) = ?;", ((MyPlayer) user).getNume().toLowerCase()) == 0) {
                    System.err.println("Nu s-a putut şterge nimic din baza de date!");
                    System.exit(0);
                }
                if(dbService.delete("DELETE FROM public.\"Map\" WHERE LOWER(username) = ?;", ((MyPlayer) user).getNume().toLowerCase()) == 0) {
                    System.err.println("Nu s-a putut şterge tot din baza de date!");
                    System.exit(0);
                }
            }
        } catch (PSQLException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("A apărut o eroare la conectarea la baza de date.");
            e.printStackTrace();
        }
        if (!input.toLowerCase().equals("close")) {
            AuditService.getInstance().logAction("Game Ended");
            System.out.println("Bravo, ai scăpat din casă!");
        }
        System.out.println("Play Time: " + ((MyPlayer) user).getTime().timeSec() / 60 + " minute şi " + (((MyPlayer) user).getTime().timeSec() - (((MyPlayer) user).getTime().timeSec() / 60) * 60) + " secunde.");
        System.out.println("Număr de comenzi rulate: " + ((MyPlayer) user).getTime().stepsTaken());
        if(!input.toLowerCase().equals("close")) {
            long check = (540000 - ((MyPlayer) user).getTime().stepsTaken() * ((MyPlayer) user).getTime().timeSec());
            String lastDigits = "" + check;
            lastDigits = "" + lastDigits.charAt(3) + lastDigits.charAt(4) + lastDigits.charAt(5);
            System.out.println("Scor final: " + (540000 - ((MyPlayer) user).getTime().stepsTaken() * ((MyPlayer) user).getTime().timeSec()) / 1000 + " " + lastDigits);
            try {
                ResultSet ps = dbService.select("SELECT * FROM public.\"Leaderboard\" WHERE LOWER(\"Username\") = ? AND \"Score\" <= ?;", ((MyPlayer) user).getNume().toLowerCase(), ((int) check));
                if(ps.next()) {
                    if(dbService.update("UPDATE public.\"Leaderboard\" SET \"Score\" = ?, \"Escape Time\" = ? WHERE \"Username\" = ?;", ((int) check), ((MyPlayer) user).getTime().timeSec() / 60 + " minute şi " + (((MyPlayer) user).getTime().timeSec() - (((MyPlayer) user).getTime().timeSec() / 60) * 60) + " secunde", ((MyPlayer) user).getNume()) == 0) {
                        System.err.println("Nu s-a putut actuliza nimic din baza de date!");
                        System.exit(0);
                    }
                    AuditService.getInstance().logAction("New High Score");
                }
                else {
                    ResultSet rs = dbService.select("SELECT * FROM public.\"Leaderboard\" WHERE LOWER(\"Username\") = ?;", ((MyPlayer) user).getNume().toLowerCase());
                    if(!rs.next()) {
                        if(dbService.insert("INSERT INTO public.\"Leaderboard\"(\"Username\", \"Score\", \"Escape Time\") VALUES (?, ?, ?);", ((MyPlayer) user).getNume(), ((int) check), ((MyPlayer) user).getTime().timeSec() / 60 + " minute şi " + (((MyPlayer) user).getTime().timeSec() - (((MyPlayer) user).getTime().timeSec() / 60) * 60) + " secunde") == 0) {
                            System.err.println("Nu s-a putut insera nimic în baza de date!");
                            System.exit(0);
                        }
                        AuditService.getInstance().logAction("First Score");
                    }
                }
                System.out.print("Doriţi să vedeţi Leaderboard-ul? y/n: ");
                input = scanner.nextLine();
                if(input.toLowerCase().equals("y")) {
                    int topTen = 10;
                    ResultSet rs = dbService.select("SELECT * FROM public.\"Leaderboard\" ORDER BY \"Score\" DESC  LIMIT ?;", topTen);
                    System.out.println("Leaderboard:");
                    int i = 1;
                    while(rs.next()) {
                        if(rs.getString("Username").toLowerCase().equals(((MyPlayer) user).getNume().toLowerCase()))
                            System.out.println("\u001B[32m" + i + ". " + rs.getString("Username") + " (score: " + rs.getString("Score") + " în " + rs.getString("Escape Time") + ")" + "\u001B[0m");
                        else System.out.println(i + ". " + rs.getString("Username") + " (score: " + rs.getString("Score") + " în " + rs.getString("Escape Time") + ")");
                        i += 1;
                    }
                    rs = dbService.select("SELECT *, (SELECT COUNT(*) FROM public.\"Leaderboard\" WHERE \"Score\" > l.\"Score\") + 1 AS position FROM public.\"Leaderboard\" l WHERE LOWER(\"Username\") = ?;", ((MyPlayer) user).getNume().toLowerCase());
                    rs.next();
                    if(rs.getInt("position") > 10)
                        System.out.println("...\n" + "\u001B[33m" + rs.getInt("position") + ". " + rs.getString("Username") + " (score: " + rs.getString("Score") + " în " + rs.getString("Escape Time") + ")" + "\u001B[0m");
                    AuditService.getInstance().logAction("Accessed Leaderboard");
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
            try {
                if(dbService.insert("INSERT INTO public.\"Player\"(nume, \"time\", items) VALUES (?, ?, ?);", ((MyPlayer) user).getNume(), gson.toJson(((MyPlayer) user).getTime()), gson.toJson(((MyPlayer) user).getItems())) == 0) {
                    System.err.println("Nu s-a putut insera nimic în baza de date!");
                    System.exit(0);
                }
                if(dbService.insert("INSERT INTO public.\"Map\"(username, saved_game, last_state) VALUES (?, ?, ?);", ((MyPlayer) user).getNume(), gson.toJson(camere), gson.toJson(currentRoom)) == 0) {
                    System.err.println("Nu s-a putut insera nimic în baza de date!");
                    System.exit(0);
                }
                AuditService.getInstance().logAction("Game Saved");
            } catch (PSQLException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
        try {
            ((MyDatabase) dbService).close();
        } catch (PSQLException e) {
            e.printStackTrace();
        }
    }
}