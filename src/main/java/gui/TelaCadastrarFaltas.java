package gui;

import model.AlunoTurma;
import model.Professor;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class TelaCadastrarFaltas extends JFrame {

    public TelaCadastrarFaltas(UsuarioService usuarioService, TurmaService turmaService, Professor professor, AlunoTurma at) {
        setTitle("Cadastrar Faltas");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel lblFaltas = new JLabel("Número de Faltas:");
        JTextField txtFaltas = new JTextField(5);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(lblFaltas);
        panel.add(txtFaltas);
        panel.add(btnSalvar);
        panel.add(btnVoltar);

        add(panel);

        btnSalvar.addActionListener(e -> {
            try {
                int faltas = Integer.parseInt(txtFaltas.getText().trim());

                if (faltas < 0) {
                    JOptionPane.showMessageDialog(this, "O número de faltas não pode ser negativo.");
                    return;
                }

                turmaService.atualizarFaltas(at.getNomeTurma(), at.getAluno().getMatricula(), faltas, professor);
                JOptionPane.showMessageDialog(this, "Faltas atualizadas com sucesso!");

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
