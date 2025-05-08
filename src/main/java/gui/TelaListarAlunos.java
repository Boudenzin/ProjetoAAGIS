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

public class TelaListarAlunos extends JFrame {
    private UsuarioService usuarioService;
    private TurmaService turmaService;
    private Professor professor;

    private DefaultListModel<AlunoTurma> listModel;
    private JList<AlunoTurma> listaAlunos;

    public TelaListarAlunos(UsuarioService usuarioService, TurmaService turmaService, Professor professor) {
        this.usuarioService = usuarioService;
        this.turmaService = turmaService;
        this.professor = professor;
        this.listModel = new DefaultListModel<>();
        this.listaAlunos = new JList<>(listModel);

        setTitle("Listar Alunos da Turma");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblTurma = new JLabel("Nome da Turma:");
        JTextField txtTurma = new JTextField(20);
        JButton btnListar = new JButton("Listar Alunos");
        JButton btnVerDetalhes = new JButton("Ver Detalhes");
        JButton btnVoltar = new JButton("Voltar");

        JPanel topPanel = new JPanel();
        topPanel.add(lblTurma);
        topPanel.add(txtTurma);
        topPanel.add(btnListar);

        listaAlunos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(listaAlunos);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnVerDetalhes);
        bottomPanel.add(btnVoltar);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Ação: Listar alunos da turma
        btnListar.addActionListener(e -> {
            String nomeTurma = txtTurma.getText().trim();
            if (nomeTurma.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o nome da turma.");
                return;
            }

            try {
                Turma turma = turmaService.buscarTurma(nomeTurma);

                if (!turma.getProfessor().getUsuario().equalsIgnoreCase(professor.getUsuario())) {
                    JOptionPane.showMessageDialog(this, "Você não tem acesso a essa turma.");
                    return;
                }

                List<AlunoTurma> alunos = turma.getParticipantes();
                listModel.clear();

                if (alunos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum aluno cadastrado nesta turma.");
                } else {
                    for (AlunoTurma at : alunos) {
                        listModel.addElement(at);
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        // Ação: Ver detalhes do aluno selecionado
        btnVerDetalhes.addActionListener(e -> {
            AlunoTurma at = listaAlunos.getSelectedValue();
            if (at != null) {
                new TelaDetalhesAluno(usuarioService, turmaService, professor, at).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um aluno da lista.");
            }
        });

        // Ação: Voltar
        btnVoltar.addActionListener(e -> {
            new TelaProfessor(usuarioService, turmaService, professor).setVisible(true);
            this.dispose();
        });
    }
}
