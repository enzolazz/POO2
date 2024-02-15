package window;

import managers.*;
import javax.swing.*;
import java.awt.*;

public class ListingWindow {
    private InputManager dados;
    private NavigationManager navegacao;

    public ListingWindow(JFrame janela, InputManager dados) {
        this.dados = dados;
        navegacao = new NavigationManager(janela, "Listagem", this.dados);

        JPanel text = new JPanel();

        JTextArea lista = new JTextArea();
        lista.append(dados.getItems());
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setPreferredSize(new Dimension(250, 110));
        text.add(scrollPane);

        JPanel botoes = new JPanel(new FlowLayout());
        for(JButton botao: navegacao.getButtons()) {
            botoes.add(botao);
        }

        janela.getContentPane().add(new HeaderManager("Listagem").getHeader(), BorderLayout.NORTH);
        janela.getContentPane().add(text, BorderLayout.CENTER);
        janela.getContentPane().add(botoes, BorderLayout.SOUTH);

        janela.revalidate();
        janela.repaint();
    }
}
