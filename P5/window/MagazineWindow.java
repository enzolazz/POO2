package window;

import managers.*;
import javax.swing.*;
import java.awt.*;

public class MagazineWindow {
    private FieldManager campo;
    private InputManager dados;
    private NavigationManager navegacao;
    private JPanel corpo;

    public MagazineWindow(JFrame janela, InputManager dados) {
        this.dados = dados;
        navegacao = new NavigationManager(janela, "Revista", this.dados);
        campo = new FieldManager(new JPanel());

        fields();

        corpo = campo.getPannel();

        JPanel botoes = new JPanel(new FlowLayout());
        JButton incluir = dados.button(corpo, "Revista");

        botoes.add(incluir);
        for(JButton botao: navegacao.getButtons()) {
            botoes.add(botao);
        }


        janela.getContentPane().add(new HeaderManager("Revistas", 18).getHeader(), BorderLayout.NORTH);
        janela.getContentPane().add(corpo, BorderLayout.CENTER);
        janela.getContentPane().add(botoes, BorderLayout.SOUTH);

        janela.revalidate();
        janela.repaint();
    }

    private void fields() {
        campo.inputField("Título", 20);
        campo.inputField("Org.", 20);
        campo.inputField("Vol.", 2);
        campo.inputField("Nro.", 2);
        campo.inputField("Ano", 4);
    }
}
