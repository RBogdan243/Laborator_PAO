package Service;

import Repository.impl.Item;

public interface GameService {
    void look();
    void search();
    Item check(int object);
    Item safe(int Try);
    Boolean exit(String key);
}
