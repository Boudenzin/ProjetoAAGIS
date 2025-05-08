package gui;

import model.AlunoTurma;
import model.Professor;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class TelaDetalhesAluno extends JFrame {
    private UsuarioService usuarioService;
    private TurmaService turmaService;
    private Professor professor;
    private AlunoTurma alunoTurma;

    public TelaDetalhesAluno(UsuarioService usuarioService, TurmaService turmaService,
                             Professor professor, AlunoTurma alunoTurma) {
        this.usuarioService = usuarioService;
        this.turmaService = turmaService;
        this.professor = professor;
        this.alunoTurma = alunoTurma;

        setTitle("Detalhes do Aluno");
        setSize(400, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Nome: " + alunoTurma.getAluno().getNome()));
        panel.add(new JLabel("Matrícula: " + alunoTurma.getAluno().getMatricula()));
        panel.add(new JLabel("Curso: " + alunoTurma.getAluno().getCurso()));
        panel.add(new JLabel("Faltas: " + alunoTurma.getFaltas()));

        for (int unidade = 1; unidade <= 3; unidade++) {
            Double nota = alunoTurma.getNotaDaUnidade(unidade);
            String textoNota = (nota != null) ? String.format("%.2f", nota) : "Sem nota";
            panel.add(new JLabel("Nota Unidade " + unidade + ": " + textoNota));
        }

        JButton btnVoltar = new JButton("Voltar");
        JButton btnCadastrarNota = new JButton("Cadastrar Nota");
        JButton btnCadastrarPresenca = new JButton("Cadastrar Presença");


        btnVoltar.addActionListener(e -> {
            new TelaListarAlunos(usuarioService, turmaService, professor).setVisible(true);
            this.dispose();
        });

        btnCadastrarNota.addActionListener(e -> {
            new TelaCadastroNota(usuarioService, turmaService, professor, alunoTurma).setVisible(true);
            this.setVisible(false);
        });

        btnCadastrarPresenca.addActionListener(e -> {
            new TelaCadastrarFaltas(usuarioService, turmaService, professor, alunoTurma).setVisible(true);
            this.setVisible(false);
        });

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnCadastrarNota);
        btnPanel.add(btnCadastrarPresenca);
        btnPanel.add(btnVoltar);


        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }
}
