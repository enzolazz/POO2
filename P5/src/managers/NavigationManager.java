package managers;

import window.BookWindow;
import window.ListingWindow;
import window.MagazineWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationManager {
    private JFrame janela;
    private JButton[] botoes = new JButton[2];
    private InputManager dados;

    public NavigationManager(JFrame janela, String tipo, InputManager dados) {
        this.janela = janela;
        this.dados = dados;

        switch (tipo) {
            case "Livro" -> {
                botoes[0] = magazineButton();
                botoes[1] = listingButton();
            }
            case "Revista" -> {
                botoes[0] = bookButton();
                botoes[1] = listingButton();
            }
            case "Listagem" -> {
                botoes[0] = bookButton();
                botoes[1] = magazineButton();
            }
        }
    }

    public JButton[] getButtons() {
        return botoes;
    }

    private JButton bookButton() {
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

    private JButton magazineButton() {
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

    private JButton listingButton() {
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
