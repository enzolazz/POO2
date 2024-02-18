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

        JTextArea books = new JTextArea();
        books.append(dados.getItems());
        JScrollPane scrollBooks = new JScrollPane(books);
        scrollBooks.setPreferredSize(new Dimension(300, 180));

        text.add(scrollBooks);

        JPanel botoes = new JPanel(new FlowLayout());
        for(JButton botao: navegacao.getButtons()) {
            botoes.add(botao);
        }

        janela.getContentPane().add(new HeaderManager("Listagem", 18).getHeader(), BorderLayout.NORTH);
        janela.getContentPane().add(text, BorderLayout.CENTER);
        janela.getContentPane().add(botoes, BorderLayout.SOUTH);

        janela.revalidate();
        janela.repaint();
    }
}
