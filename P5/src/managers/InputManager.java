package managers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import exceptions.KeyAlreadyStored;
import items.*;

public class InputManager {
    private DataManager itens;
    private JFrame janela;

    public InputManager(JFrame janela, DataManager itens) {
        this.janela = janela;
        this.itens = itens;
    }

    private List<JTextField> include(JPanel painel) {
        List<JTextField> campos = new ArrayList<>();
        for (Component grid : painel.getComponents()) {
            campos.add((JTextField) ((JPanel) grid).getComponents()[1]);
        }

        for (JTextField campo : campos) {
            if (campo.getText().isEmpty()) {
                return new ArrayList<>();
            }
        }

        return campos;
    }

    public JButton button(JPanel text, String tipo) {
        JButton botao = new JButton("Incluir");
        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<JTextField> lista = include(text);
                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(janela, "Por favor, preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        switch (tipo) {
                            case "Livro" -> {
                                int ano = Integer.parseInt(lista.getLast().getText());
                                itens.insert(new Book(lista.get(0).getText(), lista.get(1).getText(), ano));
                            }
                            case "Revista" -> {
                                int ano = Integer.parseInt(lista.getLast().getText());
                                int nro = Integer.parseInt(lista.get(lista.size() - 2).getText());
                                int vol = Integer.parseInt(lista.get(lista.size() - 3).getText());
                                itens.insert(new Magazine(lista.get(0).getText(), lista.get(1).getText(), vol, nro, ano));
                            }
                        }
                    } catch (KeyAlreadyStored k) {
                        JOptionPane.showMessageDialog(janela, "Chave primária já inserida.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    } catch (Exception a) {
                        JOptionPane.showMessageDialog(janela, "Por favor, insira números válidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    } finally {
                        for (JTextField l : lista) {
                            l.setText("");
                        }
                    }
                }
            }
        });
        return botao;
    }

    public String getItems() {
        return itens.show_items();
    }

    public String getBooks() {
        return itens.show_books();
    }

    public String getMagazines() {
        return itens.show_magazines();
    }
}
