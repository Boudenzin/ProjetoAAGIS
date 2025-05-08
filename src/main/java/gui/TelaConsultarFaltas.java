package gui;

import model.Aluno;
import model.AlunoTurma;
import model.Turma;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TelaConsultarFaltas extends JFrame {

    public TelaConsultarFaltas(UsuarioService usuarioService, TurmaService turmaService, Aluno aluno) {
        setTitle("Consultar Faltas");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        try {
            List<Turma> turmas = turmaService.getTurmasDoAluno(aluno);
            if (turmas.isEmpty()) {
                textArea.setText("Você não está matriculado em nenhuma turma.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Turma turma : turmas) {
                    sb.append("Turma: ").append(turma.getNome()).append(" (Professor: ")
                            .append(turma.getProfessor().getNome()).append(")\n");

                    for (AlunoTurma at : turma.getParticipantes()) {
                        if (at.getAluno().equals(aluno)) {
                            sb.append(" - Faltas: ").append(at.getFaltas()).append("\n");
                            break;
                        }
                    }
                    sb.append("\n");
                }
                textArea.setText(sb.toString());
            }
        } catch (IOException e) {
            textArea.setText("Erro ao carregar as faltas: " + e.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            new TelaAluno(usuarioService, turmaService, aluno).setVisible(true);
            this.dispose();
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnVoltar, BorderLayout.SOUTH);

        add(panel);
    }
}
