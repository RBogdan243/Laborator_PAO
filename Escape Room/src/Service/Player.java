package Service;

import Repository.impl.Item;
import repository.impl.Neighbor;

public interface Player {
    void AddItem(Item add);
    void ShowInventory();
    Boolean CheckInventory(Neighbor vein);
    void TimerSteps();
    void TimerGame(long sec);
    String getKey();
}
