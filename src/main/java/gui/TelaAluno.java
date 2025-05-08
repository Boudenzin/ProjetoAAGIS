package gui;

import model.Aluno;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class TelaAluno extends JFrame {

    private UsuarioService usuarioService;
    private TurmaService turmaService;
    private Aluno aluno;

    public TelaAluno(UsuarioService usuarioService, TurmaService turmaService, Aluno aluno) {
        this.usuarioService = usuarioService;
        this.turmaService = turmaService;
        this.aluno = aluno;

        setTitle("Área do Aluno - " + aluno.getNome());
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnVerTurmas = new JButton("Ver Turmas");
        JButton btnConsultarNotas = new JButton("Consultar Notas");
        JButton btnConsultarFaltas = new JButton("Consultar Faltas");
        JButton btnVoltar = new JButton("Voltar");

        btnVerTurmas.addActionListener(e -> {
            new TelaVerTurmas(usuarioService, turmaService, aluno).setVisible(true);
            this.setVisible(false);
        });

        btnConsultarNotas.addActionListener(e -> {
            new TelaConsultarNotas(usuarioService, turmaService, aluno).setVisible(true);
            this.setVisible(false);
        });

        btnConsultarFaltas.addActionListener(e -> {
            new TelaConsultarFaltas(usuarioService, turmaService, aluno).setVisible(true);
            this.setVisible(false);
        });

        btnVoltar.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            this.setVisible(false);
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.add(btnVerTurmas);
        panel.add(btnConsultarNotas);
        panel.add(btnConsultarFaltas);
        panel.add(btnVoltar);

        add(panel);
    }
}
