package managers;

import java.util.ArrayList;
import java.util.List;
import items.Item;

public class DataManager {
    private List<Item> itens = new ArrayList<>();

    public DataManager() {
    }

    public void insert(Item item) {
        itens.add(item);
    }

    public void remove(Item item) {
        itens.remove(item);
    }

    public String show_items() {
        String lista = "";
        for (Item item: itens) {
            lista = lista.concat(item.description());
        }

        return lista;
    }

    public List<Item> getItems() { return itens; }
}
