package gui;

import javax.swing.*;
import java.awt.event.*;
import gui.*;

public class TelaInicial extends JFrame {
    public TelaInicial() {
        setTitle("Sistema Escolar");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnProfessor = new JButton("Área do Professor");
        JButton btnAluno = new JButton("Área do Aluno");

        btnProfessor.addActionListener(e -> {
            new AreaProfessor().setVisible(true);
            dispose();
        });

        btnAluno.addActionListener(e -> {
            new AreaAluno().setVisible(true);
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(btnProfessor);
        panel.add(btnAluno);

        add(panel);
    }

    public static void main(String[] args) {
        new TelaInicial().setVisible(true);
    }
}

