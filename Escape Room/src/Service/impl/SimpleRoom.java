package Service.impl;

import Repository.impl.Item;
import Repository.impl.Neighbor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleRoom extends Entrance {
    private String letter;
    private String Nume;
    private String letterLocation;

    public SimpleRoom(String nume, String letter, ArrayList<String> randFurniture, Neighbor[] randNeighbors, List<String> KeyList) {
        super(randFurniture, randNeighbors, KeyList, nume);
        this.Nume = nume;
        this.letter = letter;
        Random rand = new Random();
        int i = rand.nextInt(getFurniture().length);
        while(this.letterLocation == null)
            if(getFurniture()[i].item().isEmpty()) {
                this.letterLocation = getFurniture()[i].Nume();
                break;
            }
            else i = rand.nextInt(getFurniture().length);
        this.putType("SimpleRoom");
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
        else if(this.getFurniture()[object-1].Nume().equals(this.letterLocation)) {
            if(!this.letter.isEmpty())
                System.out.println("Aţi găsit o scrisoare.");
            else System.out.println("Nu aţi găsit nimic în " + this.getFurniture()[object-1].Nume());
            String Letter = this.letter;
            this.letter = "";
            return new Item("Letter", Letter);
        }
        else System.out.println("Nu aţi găsit nimic în " + this.getFurniture()[object-1].Nume());
        return new Item("", "");
    }
}
