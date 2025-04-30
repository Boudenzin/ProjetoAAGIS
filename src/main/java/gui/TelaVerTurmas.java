package gui;

import components.TurmaSistema;
import components.Turma;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaVerTurmas extends JFrame {
    private TurmaSistema sistema;

    public TelaVerTurmas(TurmaSistema sistema) {
        this.sistema = sistema;

        setTitle("Visualizar Turmas");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea areaTurmas = new JTextArea();
        areaTurmas.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTurmas);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            new AreaAluno(sistema).setVisible(true);
            dispose();
        });

        // Preencher a área de texto com as turmas cadastradas
        List<Turma> turmas = sistema.listarTurmas();
        if (turmas.isEmpty()) {
            areaTurmas.setText("Nenhuma turma cadastrada.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Turma turma : turmas) {
                sb.append("Nome da Turma: ").append(turma.getNome()).append("\n");
                sb.append("Docente: ").append(turma.getProfessor().getNome()).append("\n");
                sb.append("Alunos cadastrados: ").append(turma.getAlunos().size()).append("\n");
                sb.append("---------------------------\n");
            }
            areaTurmas.setText(sb.toString());
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(btnVoltar, BorderLayout.SOUTH);

        add(panel);
    }
}

