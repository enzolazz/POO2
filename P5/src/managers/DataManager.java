package managers;

import exceptions.KeyAlreadyStored;
import items.Item;
import database.*;

public class DataManager {
    DBListing result = new DBListing();

    public DataManager() {
        new DBConfiguration();
    }

    public void insert(Item item) throws KeyAlreadyStored {
        new DBPopulation().insert(item);
    }

    public String show_items() {
        return result.getBooksStored().concat(result.getMagazinesStored());
    }

    public String show_books() {
        return result.getBooksStored();
    }

    public String show_magazines() {
        return result.getMagazinesStored();
    }
}
