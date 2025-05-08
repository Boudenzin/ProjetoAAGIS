package gui;

import model.Aluno;
import model.Turma;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TelaVerTurmas extends JFrame {

    public TelaVerTurmas(UsuarioService usuarioService, TurmaService turmaService, Aluno aluno) {
        setTitle("Minhas Turmas");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Turmas em que estou matriculado:");
        panel.add(titulo, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listaTurmas = new JList<>(listModel);

        try {
            List<Turma> turmasDoAluno = turmaService.getTurmasDoAluno(aluno);
            for (Turma turma : turmasDoAluno) {
                listModel.addElement(turma.toString());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar as turmas do aluno: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }

        panel.add(new JScrollPane(listaTurmas), BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            new TelaAluno(usuarioService, turmaService, aluno).setVisible(true);
            this.dispose();
        });
        panel.add(btnVoltar, BorderLayout.SOUTH);

        add(panel);
    }
}
