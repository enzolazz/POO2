import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public abstract class MetodosBotoes extends MetodosPaineis {
    public MetodosBotoes() {
        super();
    }

    protected abstract void Livro();
    protected abstract void Revista();
    protected abstract void Video();
    protected abstract void Listagem();

    protected JPanel botaoNavegacao(String tipo) {
        JButton botao = new JButton(tipo);
        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                switch (tipo) {
                    case "Livros" -> {
                        Livro();
                        setSize(390, 220);
                    }
                    case "Revistas" -> {
                        Revista();
                        setSize(370, 220);
                    }
                    case "Vídeos" -> {
                        Video();
                        setSize(390, 220);
                    }
                }
                setLocationRelativeTo(MetodosBotoes.this);
            }
        });

        JPanel painelB = new JPanel();
        painelB.add(botao);

        return painelB;
    }

    protected JPanel botaoListagem() {
        JButton botao = new JButton("Listagem");
        botao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                Listagem();
                setSize(300, 250);
                setLocationRelativeTo(MetodosBotoes.this);
            }
        });

        JPanel painelB = new JPanel();
        painelB.add(botao);

        return painelB;
    }

    protected JButton botaoIncluir(JPanel text, String tipo) {
        JButton incluir = new JButton("Incluir");
        incluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<JTextField> lista = incluir(text);
                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(MetodosBotoes.this, "Por favor, preencha todos os campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        switch (tipo) {
                            case "Livro" -> {
                                int ano = Integer.parseInt(lista.getLast().getText());
                                itens.add(new Livro(lista.getFirst().getText(), lista.get(1).getText(), ano));
                            }
                            case "Revista" -> {
                                int ano = Integer.parseInt(lista.getLast().getText());
                                int nro = Integer.parseInt(lista.get(lista.size() - 2).getText());
                                int vol = Integer.parseInt(lista.get(lista.size() - 3).getText());
                                itens.add(new Revista(lista.getFirst().getText(), lista.get(1).getText(), vol, nro, ano));
                            }
                            case "Vídeo" -> {
                                int duracao = Integer.parseInt(lista.getLast().getText());
                                itens.add(new Video(lista.getFirst().getText(), lista.get(1).getText(), duracao));
                            }
                        }

                        for (JTextField l : lista) {
                            l.setText("");
                        }
                    } catch (Exception a) {
                        JOptionPane.showMessageDialog(MetodosBotoes.this, "Por favor, insira números válidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
        return incluir;
    }
}
