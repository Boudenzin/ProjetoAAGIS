package gui;

import model.AlunoTurma;
import model.Professor;
import model.Turma;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TelaConsultaTurma extends JFrame {

    public TelaConsultaTurma(UsuarioService usuarioService, TurmaService turmaService, Professor professor) {
        setTitle("Consultar Turmas");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultListModel<Turma> listModel = new DefaultListModel<>();
        JList<Turma> listaTurmas = new JList<>(listModel);
        listaTurmas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTextArea detalhesTurma = new JTextArea();
        detalhesTurma.setEditable(false);

        try {
            List<Turma> turmasDoProfessor = turmaService.listarTodasTurmas().stream()
                    .filter(turma -> turma.getProfessor().equals(professor))
                    .collect(Collectors.toList());

            for (Turma turma : turmasDoProfessor) {
                listModel.addElement(turma);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar turmas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        listaTurmas.addListSelectionListener(e -> {
            Turma turmaSelecionada = listaTurmas.getSelectedValue();
            if (turmaSelecionada != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Nome da Turma: ").append(turmaSelecionada.getNome()).append("\n");
                sb.append("Professor: ").append(turmaSelecionada.getProfessor().getNome()).append("\n\n");

                List<AlunoTurma> participantes = turmaSelecionada.getParticipantes();
                if (participantes.isEmpty()) {
                    sb.append("Nenhum aluno matriculado.");
                } else {
                    sb.append("Alunos Matriculados:\n");
                    for (AlunoTurma at : participantes) {
                        sb.append("- ").append(at.getAluno().getNome()).append("\n");
                    }
                }

                detalhesTurma.setText(sb.toString());
            }
        });

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            new TelaProfessor(usuarioService, turmaService, professor).setVisible(true);
            dispose();
        });

        JScrollPane scrollLista = new JScrollPane(listaTurmas);
        JScrollPane scrollDetalhes = new JScrollPane(detalhesTurma);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(scrollLista, BorderLayout.WEST);
        panel.add(scrollDetalhes, BorderLayout.CENTER);
        panel.add(btnVoltar, BorderLayout.SOUTH);

        scrollLista.setPreferredSize(new Dimension(180, 300));
        scrollDetalhes.setPreferredSize(new Dimension(280, 300));

        add(panel);
    }
}
