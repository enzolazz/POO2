import javax.swing.*;
import java.awt.*;

public class JanelaRevistas {
    private FieldManager campo;
    private InputManager dados;
    private Navigation navegacao;
    private JPanel corpo;

    public JanelaRevistas(JFrame janela, InputManager dados) {
        this.dados = dados;
        navegacao = new Navigation(janela, "Revista", this.dados);
        campo = new FieldManager(new JPanel());

        campos();

        corpo = campo.getPainel();

        JPanel botoes = new JPanel(new FlowLayout());
        JButton incluir = dados.botao(corpo, "Revista");

        botoes.add(incluir);
        for(JButton botao: navegacao.getBotoes()) {
            botoes.add(botao);
        }


        janela.getContentPane().add(new Header("Revistas").getHeader(), BorderLayout.NORTH);
        janela.getContentPane().add(corpo, BorderLayout.CENTER);
        janela.getContentPane().add(botoes, BorderLayout.SOUTH);

        janela.revalidate();
        janela.repaint();
    }

    private void campos() {
        campo.inputField("Título", 20);
        campo.inputField("Org.", 20);
        campo.inputField("Vol.", 2);
        campo.inputField("Nro.", 2);
        campo.inputField("Ano", 4);
    }
}
