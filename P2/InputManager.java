import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
                                itens.insert(new Book(lista.getFirst().getText(), lista.get(1).getText(), ano));
                            }
                            case "Revista" -> {
                                int ano = Integer.parseInt(lista.getLast().getText());
                                int nro = Integer.parseInt(lista.get(lista.size() - 2).getText());
                                int vol = Integer.parseInt(lista.get(lista.size() - 3).getText());
                                itens.insert(new Magazine(lista.getFirst().getText(), lista.get(1).getText(), vol, nro, ano));
                            }
                            case "Vídeo" -> {
                                int duracao = Integer.parseInt(lista.getLast().getText());
                                itens.insert(new Video(lista.getFirst().getText(), lista.get(1).getText(), duracao));
                            }
                        }

                        for (JTextField l : lista) {
                            l.setText("");
                        }
                    } catch (Exception a) {
                        JOptionPane.showMessageDialog(janela, "Por favor, insira números válidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        return botao;
    }

    public String getItems() { return itens.show_items(); }
}
