package Service.impl;

import Repository.impl.Item;
import Repository.impl.Neighbor;

import java.util.ArrayList;
import java.util.Random;

public class SecretRoom extends Entrance {
    private int Code;
    private String Nume;
    private String FinalKey;

    public SecretRoom(String nume, ArrayList<String> randFurniture, Neighbor[] randNeighbors, String Key) {
        super(updateFurniture(randFurniture), randNeighbors, new ArrayList<>(), nume);
        Random rand = new Random();
        this.Code = rand.nextInt(100, 999);
        this.Nume = nume;
        this.FinalKey = Key;
        this.putType("SecretRoom");
    }

    private static ArrayList<String> updateFurniture(ArrayList<String> randFurniture) {
        randFurniture.add("Safe");
        return randFurniture;
    }

    @Override
    public String getName() {
        return this.Nume;
    }

    public int getCode() {
        return this.Code;
    }

    @Override
    public Item check(int object) {
        if(object > this.getFurniture().length) {
            System.out.println("Element inexistent!");
            return new Item("", "");
        }
        if(this.getFurniture()[object-1].Nume().equals("Safe"))
            System.out.println("Aţi găsit un seif parolat, introduceti parola pentru al accesa.");
        else System.out.println("Nu aţi găsit nimic în " + this.getFurniture()[object-1].Nume());
        return new Item("", "");
    }

    @Override
    public Item safe(int Try) {
        if(Try == this.Code) {
            System.out.println("Aţi găsit cheia pentru Exit");
            this.Code = 8008;
            return new Item("FinalKey", this.FinalKey);
        }
        else {
            System.out.println("Parola incorectă!");
            return new Item("", "");
        }
    }
}
