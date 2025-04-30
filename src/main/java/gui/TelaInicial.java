package gui;

import javax.swing.*;
import java.awt.event.*;

import components.TurmaList;
import components.TurmaSistema;
import gui.*;

public class TelaInicial extends JFrame {

    private TurmaSistema sistema = new TurmaList();
    public TelaInicial() {
        setTitle("Sistema Escolar");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnProfessor = new JButton("Área do Professor");
        JButton btnAluno = new JButton("Área do Aluno");

        btnProfessor.addActionListener(e -> {
            new AreaProfessor(sistema).setVisible(true);
            dispose();
        });

        btnAluno.addActionListener(e -> {
            new AreaAluno(sistema).setVisible(true);
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

