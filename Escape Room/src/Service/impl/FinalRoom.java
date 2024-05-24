package Service.impl;

import Repository.impl.Neighbor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FinalRoom extends Entrance {
    private String lock;

    public FinalRoom(ArrayList<String> randFurniture, Neighbor[] randNeighbors, List<String> KeyList) {
        super(randFurniture, randNeighbors, KeyList, "Ieşirea");
        Random rand = new Random();
        this.lock = rand.toString() + rand.nextInt();
        this.putType("FinalRoom");
    }

    @Override
    public String getName() {
        return "Ieşirea";
    }

    public String getLock() {
        return this.lock;
    }

    @Override
    public Boolean exit(String key) {
        if(key.equals(this.lock))
            return true;
        else return false;
    }
}
