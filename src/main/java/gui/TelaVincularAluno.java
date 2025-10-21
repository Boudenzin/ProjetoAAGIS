package gui;

import exceptions.AlunoJaMatriculadoException;
import exceptions.TurmaNaoEncontradaException;
import model.Aluno;
import model.Professor;
import model.Turma;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class TelaVincularAluno extends JFrame {

    public TelaVincularAluno(UsuarioService usuarioService, TurmaService turmaService, Professor professor) {
        setTitle("Cadastrar Aluno em Turma");
        setSize(450, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));

        JLabel lblTurmas = new JLabel("Selecione a turma:");
        JComboBox<String> comboTurmas = new JComboBox<>();
        try {
            List<Turma> turmasDoProfessor = turmaService.getTurmasDoProfessor(professor);
            for (Turma turma : turmasDoProfessor) {
                comboTurmas.addItem(turma.getNome());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar as turmas: " + e.getMessage());
        }

        JLabel lblMatricula = new JLabel("Digite a matrícula do aluno:");
        JTextField txtMatricula = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar Aluno");
        JButton btnVoltar = new JButton("Voltar");

        btnCadastrar.addActionListener(e -> {
            String nomeTurma = (String) comboTurmas.getSelectedItem();
            String matricula = txtMatricula.getText().trim();

            if (nomeTurma == null || matricula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            Aluno aluno = usuarioService.buscarAlunoPorMatricula(matricula);
            if (aluno == null) {
                JOptionPane.showMessageDialog(this, "Aluno não encontrado.");
                return;
            }

            try {
                turmaService.adicionarAluno(nomeTurma, aluno);
                JOptionPane.showMessageDialog(this, "Aluno adicionado com sucesso!");

            } catch (TurmaNaoEncontradaException | AlunoJaMatriculadoException ex) {

                JOptionPane.showMessageDialog(this,
                        ex.getMessage(), // <-- A MÁGICA ACONTECE AQUI
                        "Aviso de Matrícula",
                        JOptionPane.WARNING_MESSAGE);

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(this,
                        "Erro inesperado ao salvar os dados: " + ex.getMessage(),
                        "Erro de Arquivo",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this,
                        "Ocorreu um erro: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> {
            new TelaProfessor(usuarioService, turmaService, professor).setVisible(true);
            dispose();
        });

        panel.add(lblTurmas);
        panel.add(comboTurmas);
        panel.add(lblMatricula);
        panel.add(txtMatricula);
        panel.add(btnCadastrar);
        panel.add(btnVoltar);

        add(panel);
    }
}
