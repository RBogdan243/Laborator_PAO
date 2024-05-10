package Service.impl;

import Repository.impl.Item;
import repository.impl.Neighbor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PortraitRoom extends Entrance {
    private String Info;
    private String Nume;

    public PortraitRoom(String nume, int Code, ArrayList<String> randFurniture, Neighbor[] randNeighbors, List<String> KeyList) {
        super(updateFurniture(randFurniture), randNeighbors, KeyList, nume);
        this.Nume = nume;
        Random rand = new Random();
        this.Info = "Fotografie făcută la data de " + Code/100 + '.' + rand.nextInt(1,12) + '.' + rand.nextInt(1990, 2018) + " în care se remarcă " + (Code % 100) + " copaci scrijeliţi.";
        this.putType("PortraitRoom");
    }

    private static ArrayList<String> updateFurniture(ArrayList<String> randFurniture) {
        randFurniture.add("Portrait");
        return randFurniture;
    }

    @Override
    public String getName() {
        return this.Nume;
    }

    @Override
    public Item check(int object) {
        if(object > this.getFurniture().length) {
            System.out.println("Element inexistent!");
            return new Item("", "");
        }
        if(!this.getFurniture()[object-1].item().isEmpty()) {
            return new Item("Key", this.getFurniture()[object-1].item());
        }
        else if(this.getFurniture()[object-1].Nume().equals("Portrait")) {
            System.out.println("Aţi găsit un tablou cu o " + this.Info);
            return new Item("", "");
        }
        else System.out.println("Nu aţi găsit nimic în " + this.getFurniture()[object-1].Nume());
        return new Item("", "");
    }
}
