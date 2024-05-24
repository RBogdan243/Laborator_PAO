package Service.impl;
import Repository.impl.Item;
import Repository.impl.Furniture;
import Repository.impl.Neighbor;
import Service.GameService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Entrance implements GameService {
    private String key;
    private Furniture[] furniture;
    private Neighbor[] neighbors;
    private String Type = "Entrance";

    public Entrance(ArrayList<String> randFurniture, Neighbor[] randNeighbors, List<String> KeyList, String nume) {
        this.furniture = new Furniture[4];
        this.neighbors = randNeighbors;
        Random rand = new Random();
        int j = rand.nextInt(this.furniture.length);
        boolean Ok;
        int k = rand.nextInt(randNeighbors.length);
        List<Integer> a = new ArrayList<>();
        if(!KeyList.isEmpty()) {
            Ok = false;
            while (true)
                if(KeyList.contains(randNeighbors[k].Nume()) && !randNeighbors[k].Nume().equals("\uD83D\uDEAB")) {
                    this.key = randNeighbors[k].Nume();
                    Ok = true;
                    break;
                }
                else {
                    if(!a.contains(k))
                        a.add(k);
                    else if(a.size() == randNeighbors.length)
                        break;
                    k = rand.nextInt(randNeighbors.length);
                }
            if(!Ok)
                while(this.key == null || this.key.equals(nume)) {
                    this.key = KeyList.get(rand.nextInt(KeyList.size()));
                    if((this.key.equals("Ieşirea") || this.key.equals("Bucătăria")) && KeyList.contains("Sufrageria"))
                        this.key = "Sufrageria";
                }
        }
        else this.key = "";
        if(getName() != null && getName().equals("Ieşirea"))
            while(this.key == null || this.key.equals(nume) || this.key.equals("Sufrageria"))
                this.key = KeyList.get(rand.nextInt(KeyList.size()));
        KeyList.remove(this.key);
        if(randFurniture.contains("Safe")) {
            this.furniture = new Furniture[5];
            randFurniture.remove("Safe");
            this.furniture[4] = new Furniture("Safe", "");
        }
        else if(randFurniture.contains("Portrait")) {
            this.furniture = new Furniture[5];
            randFurniture.remove("Portrait");
            this.furniture[4] = new Furniture("Portrait", "");
        }
        for(int i = 0; i < 4; i++) {
            int randIndex = rand.nextInt(randFurniture.size());
            String furnitureItem = randFurniture.get(randIndex);
            if(i == j) {
                if(!furnitureItem.equals("\uD83D\uDEAB")) {
                    randFurniture.remove(randIndex);
                }
                this.furniture[i] = new Furniture(furnitureItem, this.key);
            }
            else {
                if(!furnitureItem.equals("\uD83D\uDEAB")) {
                    randFurniture.remove(randIndex);
                }
                this.furniture[i] = new Furniture(furnitureItem, "");
            }
        }
    }

    public String getName() {
        return "Intrarea";
    }

    public Furniture[] getFurniture() {
        return this.furniture;
    }

    public Neighbor[] getNeighbors() {
        return this.neighbors;
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public void look() {
        String[] direction = {"↑", "←", "→", "↓"};
        for(int i = 0; i < 4; i++) {
            int space = this.neighbors[i].Nume().length() / 2;
            String text = String.format("%" + space + "s%s%" + space + "s %s", "", direction[i], "", this.neighbors[i].Nume());
            System.out.println(text);
        }
    }

    @Override
    public void search() {
        System.out.println("Selectează un obiect pe care să-l verifici:");
        for(int i = 0; i < this.furniture.length; i++) {
            System.out.println((i+1) + " " + this.furniture[i].Nume());
        }
    }

    @Override
    public Item check(int object) {
        if(object > this.furniture.length) {
            System.out.println("Element inexistent!");
            return new Item("", "");
        }
        if(!this.furniture[object - 1].item().isEmpty()) {
            return new Item("Key", this.key);
        }
        else System.out.println("Nu aţi găsit nimic în " + this.furniture[object-1].Nume());
        return new Item("", "");
    }

    @Override
    public Item safe(int a) {
        return new Item("", "");
    }

    @Override
    public Boolean exit(String a) {
        return false;
    }

    public void putType(String put) {this.Type = put;}
}
