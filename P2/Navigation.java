import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Navigation {
    private JFrame janela;
    private JButton[] botoes = new JButton[3];
    private InputManager dados;

    public Navigation(JFrame janela, String tipo, InputManager dados) {
        this.janela = janela;
        this.dados = dados;

        switch (tipo) {
            case "Livro" -> {
                botoes[0] = botaoRevistas();
                botoes[1] = botaoVideos();
                botoes[2] = botaoListagem();

            }
            case "Revista" -> {
                botoes[0] = botaoLivros();
                botoes[1] = botaoVideos();
                botoes[2] = botaoListagem();

            }
            case "Vídeo" -> {
                botoes[0] = botaoLivros();
                botoes[1] = botaoRevistas();
                botoes[2] = botaoListagem();

            }
            case "Listagem" -> {
                botoes[0] = botaoLivros();
                botoes[1] = botaoRevistas();
                botoes[2] = botaoVideos();

            }
        }
    }

    public JButton[] getBotoes() {
        return botoes;
    }

    private JButton botaoLivros() {
        JButton botao = new JButton("Livros");
        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                janela.getContentPane().removeAll();

                new BookWindow(janela, dados);
                janela.setSize(390, 220);

                janela.setLocationRelativeTo(janela);
            }
        });

        return botao;
    }

    private JButton botaoRevistas() {
        JButton botao = new JButton("Revistas");
        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                janela.getContentPane().removeAll();

                new MagazineWindow(janela, dados);
                janela.setSize(370, 220);

                janela.setLocationRelativeTo(janela);
            }
        });

        return botao;
    }

    private JButton botaoVideos() {
        JButton botao = new JButton("Vídeos");
        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                janela.getContentPane().removeAll();

                new VideoWindow(janela, dados);
                janela.setSize(390, 220);

                janela.setLocationRelativeTo(janela);
            }
        });

        return botao;
    }

    private JButton botaoListagem() {
        JButton botao = new JButton("Listagem");
        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                janela.getContentPane().removeAll();

                new ListingWindow(janela, dados);
                janela.setSize(300, 250);

                janela.setLocationRelativeTo(janela);
            }
        });

        return botao;
    }
}
