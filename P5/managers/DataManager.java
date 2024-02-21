package managers;

import exceptions.KeyAlreadyStored;
import items.Item;
import database.*;

public class DataManager {
    DBListing result = new DBListing();

    public DataManager() {
    }

    public void insert(Item item) throws KeyAlreadyStored {
        new DBPopulation().insert(item);
    }

    public String show_items() {
        return result.getItems();
    }

    public String show_books() {
        return result.getBooksStored();
    }

    public String show_magazines() {
        return result.getMagazinesStored();
    }
}
