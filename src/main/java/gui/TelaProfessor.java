package gui;

import model.Professor;
import service.UsuarioService;
import service.TurmaService;

import javax.swing.*;
import java.awt.*;

public class TelaProfessor extends JFrame {

    public TelaProfessor(UsuarioService usuarioService, TurmaService turmaService, Professor prof) {
        setTitle("Área do Professor");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnCadastrarTurma = new JButton("Cadastrar Turma");
        JButton btnConsultarTurma = new JButton("Consultar Turma");
        JButton btnVincularAluno = new JButton("Cadastrar Aluno");
        JButton btnListarAlunos = new JButton("Listar Alunos da Turma");
        JButton btnVoltar = new JButton("Voltar");

        btnCadastrarTurma.addActionListener(e -> {
            new TelaCadastroTurma(usuarioService, turmaService, prof).setVisible(true);
            this.setVisible(false);
        });

        btnConsultarTurma.addActionListener(e -> {
            new TelaConsultaTurma(usuarioService, turmaService, prof).setVisible(true);
            this.setVisible(false);
        });

        btnVincularAluno.addActionListener(e -> {
            new TelaVincularAluno(usuarioService, turmaService, prof).setVisible(true);
            this.setVisible(false);
        });

        btnListarAlunos.addActionListener(e -> {
            new TelaListarAlunos(usuarioService, turmaService, prof).setVisible(true);
            this.setVisible(false);
        });

        btnVoltar.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            this.dispose();
        });

        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(btnCadastrarTurma);
        panel.add(btnConsultarTurma);
        panel.add(btnVincularAluno);
        panel.add(btnListarAlunos);
        panel.add(btnVoltar);

        add(panel);
    }
}
