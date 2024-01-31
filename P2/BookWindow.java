import javax.swing.*;
import java.awt.*;

public class BookWindow {
    private FieldManager campo;
    private InputManager dados;
    private Navigation navegacao;
    private JPanel corpo;

    public BookWindow(JFrame janela, InputManager dados) {
        this.dados = dados;
        navegacao = new Navigation(janela, "Livro", this.dados);
        campo = new FieldManager(new JPanel());

        campos();

        corpo = campo.getPainel();

        JPanel botoes = new JPanel(new FlowLayout());
        JButton incluir = dados.botao(corpo, "Livro");

        botoes.add(incluir);
        for(JButton botao: navegacao.getBotoes()) {
            botoes.add(botao);
        }


        janela.getContentPane().add(new Header("Livros").getHeader(), BorderLayout.NORTH);
        janela.getContentPane().add(corpo, BorderLayout.CENTER);
        janela.getContentPane().add(botoes, BorderLayout.SOUTH);

        janela.revalidate();
        janela.repaint();
    }

    private void campos() {
        campo.inputField("Título", 20);
        campo.inputField("Autor", 20);
        campo.inputField("Ano", 4);
    }
}
