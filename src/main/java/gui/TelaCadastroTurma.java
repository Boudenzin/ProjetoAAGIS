package gui;

import exceptions.TurmaJaCriadaException;
import model.Professor;
import model.Turma;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TelaCadastroTurma extends JFrame {

    public TelaCadastroTurma(UsuarioService usuarioService, TurmaService turmaService, Professor professor) {
        setTitle("Cadastrar Nova Turma");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblNome = new JLabel("Nome da Turma:");
        JTextField txtNome = new JTextField(20);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnVoltar = new JButton("Voltar");

        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText().trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                turmaService.criarTurma(nome, professor);
                JOptionPane.showMessageDialog(this, "Turma cadastrada com sucesso!");
                new TelaProfessor(usuarioService, turmaService, professor).setVisible(true);
                dispose();
            } catch (TurmaJaCriadaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Cadastro", JOptionPane.WARNING_MESSAGE);
            }

            catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a turma: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnVoltar.addActionListener(e -> {
            new TelaProfessor(usuarioService, turmaService, professor).setVisible(true);
            dispose();
        });

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(lblNome);
        panel.add(txtNome);
        panel.add(btnCadastrar);
        panel.add(btnVoltar);

        add(panel);
    }
}
