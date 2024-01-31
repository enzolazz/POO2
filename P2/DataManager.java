import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private List<Item> itens = new ArrayList<>();

    public DataManager() {
    }

    public void inserir(Item item) {
        itens.add(item);
    }

    public void remover(Item item) {
        itens.remove(item);
    }

    public String mostrar_itens() {
        String lista = "";
        for (Item item: itens) {
            lista = lista.concat(item.descricao());
        }

        return lista;
    }

    public List<Item> getItens() { return itens; }
}
