package gui;

import model.AlunoTurma;
import model.Professor;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroNota extends JFrame {

    public TelaCadastroNota(UsuarioService usuarioService, TurmaService turmaService, Professor professor, AlunoTurma at) {
        setTitle("Cadastrar Nota");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblUnidade = new JLabel("Unidade (1 a 3):");
        JTextField txtUnidade = new JTextField(5);

        JLabel lblNota = new JLabel("Nota:");
        JTextField txtNota = new JTextField(5);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(lblUnidade);
        panel.add(txtUnidade);
        panel.add(lblNota);
        panel.add(txtNota);
        panel.add(btnSalvar);
        panel.add(btnVoltar);

        add(panel);

        btnSalvar.addActionListener(e -> {
            try {
                int unidade = Integer.parseInt(txtUnidade.getText().trim());
                double nota = Double.parseDouble(txtNota.getText().trim());

                if (unidade < 1 || unidade > 3) {
                    JOptionPane.showMessageDialog(this, "A unidade deve estar entre 1 e 3.");
                    return;
                }

                turmaService.atualizarNota(at.getNomeTurma(), at.getAluno().getMatricula(), unidade, nota, professor);
                JOptionPane.showMessageDialog(this, "Nota cadastrada com sucesso!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnVoltar.addActionListener(e -> {
            new TelaDetalhesAluno(usuarioService, turmaService, professor, at).setVisible(true);
            dispose();
        });
    }
}
