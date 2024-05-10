package Service.impl;

import Repository.impl.Item;
import Repository.impl.Timer;
import repository.impl.Neighbor;
import Service.Player;

import java.util.*;

public class MyPlayer implements Player {
    private Item[] items = new Item[0];
    private Timer time = new Timer(0, 0);
    private static String Nume;
    private static MyPlayer instance;

    private MyPlayer() {}

    private MyPlayer(Item[] items_saved, Timer time_saved) {
        this.items = items_saved;
        this.time = time_saved;
    }

    public static MyPlayer createPlayer(String username) {
        if (instance == null) {
            instance = new MyPlayer();
            Nume = username;
        }
        return instance;
    }

    public static MyPlayer createPlayer(String username, Item[] items_saved, Timer time_saved) {
        if (instance == null) {
            instance = new MyPlayer(items_saved, time_saved);
            Nume = username;
        }
        return instance;
    }

    public static List<Entrance> RandomGenerate() {
        ArrayList<String> F = new ArrayList<>(Arrays.asList("Dulap", "Veoză", "Birou", "Frigider", "Cadă", "Pungă", "Haină", "Raft", "Cutie", "Pat", "Canapea", "Fotoliu", "Maşină de Scris", "Lustră", "Plantă", "Ghiozdan", "Lampă", "Cuptor", "Maşină de Spălat", "Cufăr", "Casetofon", "Ceainic Vechi", "Maşină de Cusut", "Televizor", "Chiuvetă", "Servietă", "Poşetă", "Borsetă", "Cutie Poştală", "Masă", "Model de Schelete Uman", "Glob Pământesc", "Harta Lumii", "Glob Magic", "Geanta de Sport", "Dosar"));
        List<Entrance> camere = new ArrayList<>();
        int[] sepecial = new int[2];
        int j = 0;
        Random rand = new Random();
        while(j < 2) {
            int a = rand.nextInt(3, 7);
            Boolean Ok = false;
            if(sepecial[0] == a)
                Ok = true;
            if(!Ok) {
                sepecial[j] = a;
                j ++;
            }
        }
        String order = "";
        String secret = "";
        int password = rand.nextInt(100,319);
        String[] letters = new String[0];
        List<String> Keys = new ArrayList<>(Arrays.asList("Baia", "Bibleoteca", "Dormitorul", "Holul", "Sufrageria", "Bucătăria", "Ieşirea"));
        for(int i = 0; i < 9; i++)
            if(i == 0) {
                Neighbor[] N = {new Neighbor("Baia", 0), new Neighbor("Bibleoteca", 0), new Neighbor("Dormitorul", 0), new Neighbor("\uD83D\uDEAB", 0)};
                camere.add(new SimpleRoom("Intrare", "", F, N, Keys));
                order = camere.getFirst().getKey();
            }
            else if(i == 1) {
                Neighbor[] N = {new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("Sufrageria", 0)};
                camere.add(new FinalRoom(F, N, Keys));
            }
            else if(i == 2) {
                String[] random = new String[]{"Baia", "Bibleoteca", "Dormitorul", "Holul", "Sufrageria", "Bucătăria"};
                secret = random[rand.nextInt(random.length)];
                Neighbor[] N = {new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor(secret, 0)};
                camere.add(new SecretRoom("Podul", F, N, ((FinalRoom) camere.get(1)).getLock()));
                letters = new String[]{"...\nAm fost pestetot prin lume, dar nu am găsit nicăieri un peisaj aşa frumos ca aici, cu toţi copacii aceştia frumoşi şi verzi!\n...", "...\nAdreea: Unde se duce bunicu mereu când dispare din " + secret + "?\nMihai: În pod, nu ştiai că poţi ajunge în pod din " + secret + "?\n...", "...\nAndreea: Bunicu, bunicu, care era codul pentru pod?\nBunicu: Aaaaa, dar cum să nu mai ştii dragă?\n        Codul este înscris în peisajul meu mult iubit împreună cu data la care m-am întors din călătoriile mele internaţionale, am făcut şi o poză împreună dacă nu mai ţii minte!\n...", "...\nMihai: Bunicu! Am uitat codul de la intrarea din pod, poţi să mi-l zici?\nBunicu: Offf, nepoate, trebuie să ţii şi tu minte măcar atâta lucru!\n        Codul pentru uşa de la pod este mereu data la care l-am cumpărat! O să-ţi las bonul de acum undeva ca să nu mai uiţi!\n..."};
            }
            else if(order.equals("Bibleoteca")) {
                Neighbor[] N;
                if(secret.equals("Bibleoteca"))
                    N = new Neighbor[]{new Neighbor("Sufrageria", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("Intrare", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("Podul", password)};
                else N = new Neighbor[]{new Neighbor("Sufrageria", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("Intrare", 0), new Neighbor("\uD83D\uDEAB", 0)};
                if(i == sepecial[0])
                    camere.add(new PortraitRoom("Bibleoteca", ((SecretRoom) camere.get(2)).getCode(), F, N, Keys));
                else if(i == sepecial[1])
                    camere.add(new TicketRoom("Bibleoteca", password, F, N, Keys));
                else {
                    camere.add(new SimpleRoom("Bibleoteca", letters[0], F, N, Keys));
                    String[] nou = new String[letters.length-1];
                    for(int k = 1; k < letters.length; k++)
                        nou[k-1] = letters[k];
                    letters = nou;
                }
                if(camere.get(i).getKey().equals("Ieşirea"))
                    order = camere.get(1).getKey();
                else order = camere.get(i).getKey();
            }
            else if(order.equals("Dormitorul")) {
                Neighbor[] N;
                if(secret.equals("Dormitorul"))
                    N = new Neighbor[]{new Neighbor("Holul", 0), new Neighbor("Intrare", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("Podul", password)};
                else N = new Neighbor[]{new Neighbor("Holul", 0), new Neighbor("Intrare", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0)};
                if(i == sepecial[0])
                    camere.add(new PortraitRoom("Dormitorul", ((SecretRoom) camere.get(2)).getCode(), F, N, Keys));
                else if(i == sepecial[1])
                    camere.add(new TicketRoom("Dormitorul", password, F, N, Keys));
                else {
                    camere.add(new SimpleRoom("Dormitorul", letters[0], F, N, Keys));
                    String[] nou = new String[letters.length-1];
                    for(int k = 1; k < letters.length; k++)
                        nou[k-1] = letters[k];
                    letters = nou;
                }
                if(camere.get(i).getKey().equals("Ieşirea"))
                    order = camere.get(1).getKey();
                else order = camere.get(i).getKey();
            }
            else if(order.equals("Baia")) {
                Neighbor[] N;
                if(secret.equals("Baia"))
                    N = new Neighbor[]{new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("Holul", 0), new Neighbor("Intrare", 0), new Neighbor("Podul", password)};
                else N = new Neighbor[]{new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("Holul", 0), new Neighbor("Intrare", 0)};
                if(i == sepecial[0])
                    camere.add(new PortraitRoom("Baia", ((SecretRoom) camere.get(2)).getCode(), F, N, Keys));
                else if(i == sepecial[1])
                    camere.add(new TicketRoom("Baia", password, F, N, Keys));
                else {
                    camere.add(new SimpleRoom("Baia", letters[0], F, N, Keys));
                    String[] nou = new String[letters.length-1];
                    for(int k = 1; k < letters.length; k++)
                        nou[k-1] = letters[k];
                    letters = nou;
                }
                if(camere.get(i).getKey().equals("Ieşirea"))
                    order = camere.get(1).getKey();
                else order = camere.get(i).getKey();
            }
            else if(order.equals("Holul")) {
                Neighbor[] N;
                if(secret.equals("Holul"))
                    N = new Neighbor[]{new Neighbor("Sufrageria", 0), new Neighbor("Baia", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("Dormitorul", 0), new Neighbor("Podul", password)};
                else N = new Neighbor[]{new Neighbor("Sufrageria", 0), new Neighbor("Baia", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("Dormitorul", 0)};
                if(i == sepecial[0])
                    camere.add(new PortraitRoom("Holul", ((SecretRoom) camere.get(2)).getCode(), F, N, Keys));
                else if(i == sepecial[1])
                    camere.add(new TicketRoom("Holul", password, F, N, Keys));
                else {
                    camere.add(new SimpleRoom("Holul", letters[0], F, N, Keys));
                    String[] nou = new String[letters.length-1];
                    for(int k = 1; k < letters.length; k++)
                        nou[k-1] = letters[k];
                    letters = nou;
                }
                if(camere.get(i).getKey().equals("Ieşirea"))
                    order = camere.get(1).getKey();
                else order = camere.get(i).getKey();
            }
            else if(order.equals("Sufrageria")){
                Neighbor[] N;
                if(secret.equals("Sufrageria"))
                    N = new Neighbor[]{new Neighbor("Ieşirea", 0), new Neighbor("Bibleoteca", 0), new Neighbor("Holul", 0), new Neighbor("Bucătăria", 0), new Neighbor("Podul", password)};
                else N = new Neighbor[]{new Neighbor("Ieşirea", 0), new Neighbor("Bibleoteca", 0), new Neighbor("Holul", 0), new Neighbor("Bucătăria", 0)};
                if(i == sepecial[0])
                    camere.add(new PortraitRoom("Sufrageria", ((SecretRoom) camere.get(2)).getCode(), F, N, Keys));
                else if(i == sepecial[1])
                    camere.add(new TicketRoom("Sufrageria", password, F, N, Keys));
                else {
                    camere.add(new SimpleRoom("Sufrageria", letters[0], F, N, Keys));
                    String[] nou = new String[letters.length-1];
                    for(int k = 1; k < letters.length; k++)
                        nou[k-1] = letters[k];
                    letters = nou;
                }
                if(camere.get(i).getKey().equals("Ieşirea"))
                    order = camere.get(1).getKey();
                else order = camere.get(i).getKey();
            }
            else {
                Neighbor[] N;
                if(secret.equals("Bucătăria"))
                    N = new Neighbor[]{new Neighbor("Sufrageria", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("Podul", password)};
                else N = new Neighbor[]{new Neighbor("Sufrageria", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0), new Neighbor("\uD83D\uDEAB", 0)};
                if(i == sepecial[0])
                    camere.add(new PortraitRoom("Bucătăria", ((SecretRoom) camere.get(2)).getCode(), F, N, Keys));
                else if(i == sepecial[1])
                    camere.add(new TicketRoom("Bucătăria", password, F, N, Keys));
                else {
                    camere.add(new SimpleRoom("Bucătăria", letters[0], F, N, Keys));
                    String[] nou = new String[letters.length-1];
                    for(int k = 1; k < letters.length; k++)
                        nou[k-1] = letters[k];
                    letters = nou;
                }
                if(camere.get(i).getKey().equals("Ieşirea"))
                    order = camere.get(1).getKey();
                else order = camere.get(i).getKey();
            }

        return camere;
    }

    @Override
    public void AddItem(Item add) {
        if(!Arrays.stream(this.items).anyMatch(element -> element.equals(add))) {
            Item[] Add = new Item[this.items.length+1];
            for(int i = 0; i < this.items.length; i++)
                Add[i] = this.items[i];
            Add[Add.length-1] = add;
            this.items = Add;
            if(!add.Type().equals("FinalKey") && !add.Type().equals("Clue") && !add.Type().equals("Letter"))
                System.out.println("Aţi găsit cheia pentru " + add.Nume());
            else if(!add.Type().equals("FinalKey") && !add.Type().equals("Letter"))
                System.out.println("Aţi găsit un bon de cumparaturi.");
        }
        else System.out.println("Nu mai este nimic aici.");
    }

    @Override
    public void ShowInventory() {
        for(int i = 0; i < this.items.length; i++)
            System.out.println((i+1) + " " + this.items[i].Type());
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        if(command.toLowerCase().equals("inspect")) {
            int item = scanner.nextInt();
            scanner.nextLine();
            if(this.items[item-1].Type().equals("Key"))
                System.out.println("Item-ul selectat este o cheie pentru camera " + this.items[item-1].Nume() + '.');
            else if(this.items[item-1].Type().equals("Letter"))
                System.out.println("Letter: " +this.items[item-1].Nume());
            else System.out.println("Clue: " + this.items[item-1].Nume());
        }
    }

    @Override
    public Boolean CheckInventory(Neighbor vein) {
        for(int i = 0; i < this.items.length; i++)
            if(this.items[i].Nume().equals(vein.Nume()))
                return true;
        return false;
    }

    @Override
    public String getKey() {
        for(int i = 0; i < this.items.length; i++)
            System.out.println((i+1) + " " + this.items[i].Type());
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        scanner.nextLine();
        return this.items[index-1].Nume();
    }

    @Override
    public void TimerSteps() {
        this.time = new Timer(this.time.timeSec(), this.time.stepsTaken()+1);
    }

    @Override
    public void TimerGame(long sec) {
        this.time = new Timer(sec, this.time.stepsTaken());
    }

    public Timer getTime() {
        return this.time;
    }

    public String getNume() {return Nume;}

    public Item[] getItems() {return this.items;}
}
