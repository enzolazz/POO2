import javax.swing.*;
import java.awt.*;

public class JanelaListagem {
    private InputManager dados;
    private Navigation navegacao;

    public JanelaListagem(JFrame janela, InputManager dados) {
        this.dados = dados;
        navegacao = new Navigation(janela, "Listagem", this.dados);

        JPanel text = new JPanel();

        JTextArea lista = new JTextArea();
        lista.append(dados.getItens());
        JScrollPane scrollPane = new JScrollPane(lista);
        scrollPane.setPreferredSize(new Dimension(250, 110));
        text.add(scrollPane);

        JPanel botoes = new JPanel(new FlowLayout());
        for(JButton botao: navegacao.getBotoes()) {
            botoes.add(botao);
        }

        janela.getContentPane().add(new Header("Listagem").getHeader(), BorderLayout.NORTH);
        janela.getContentPane().add(text, BorderLayout.CENTER);
        janela.getContentPane().add(botoes, BorderLayout.SOUTH);

        janela.revalidate();
        janela.repaint();
    }
}
