package Service.impl;

import Repository.impl.Item;
import Repository.impl.Neighbor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicketRoom extends Entrance {
    private String Clue;
    private String Nume;
    private String clueLocation;

    public TicketRoom(String nume, int Code, ArrayList<String> randFurniture, Neighbor[] randNeighbors, List<String> KeyList) {
        super(randFurniture, randNeighbors, KeyList, nume);
        this.Nume = nume;
        String[] luni = new String[]{"", "Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie", "Iulie", "August", "Septembrie"};
        String luna = "";
        for(int i = 1; i < 10; i++)
            if(i == Code % 10) {
                luna = luni[i];
                break;
            }
            else luna = luni[1];
        Random rand = new Random();
        this.Clue = "Bon de cumparaturi de pret total " + rand.nextDouble(1500.00) + " lei facturat la data de " + Code/10 + " " + luna + " " + rand.nextInt(1978, 2013);
        int i = rand.nextInt(getFurniture().length);
        while(this.clueLocation == null)
            if(getFurniture()[i].item().isEmpty()) {
                this.clueLocation = getFurniture()[i].Nume();
                break;
            }
            else i = rand.nextInt(getFurniture().length);
        this.putType("TicketRoom");
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
        else if(this.getFurniture()[object-1].Nume().equals(this.clueLocation)) {
            return new Item("Clue", this.Clue);
        }
        else System.out.println("Nu aţi găsit nimic în " + this.getFurniture()[object-1].Nume());
        return new Item("", "");
    }
}
