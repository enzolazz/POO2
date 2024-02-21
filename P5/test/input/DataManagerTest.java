package test.input;

import database.DBConfiguration;
import org.junit.jupiter.api.Test;

import items.Book;
import managers.DataManager;
import managers.InputManager;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;


public class DataManagerTest {
    DataManager gerenciador = new DataManager();
    InputManager dados = new InputManager(new JFrame(), gerenciador);
    @Test
    public void testInsert() {
        new DBConfiguration();

        assertEquals("", dados.getItems());

        gerenciador.insert(new Book("Titulo 1", "Autor 1", 1900));
        String esperado = "Livro: Titulo 1, Autor 1, 1900\n";

        assertEquals(esperado, dados.getItems());
    }
}
