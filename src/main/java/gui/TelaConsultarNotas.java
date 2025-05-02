package gui;

import components.Aluno;
import components.Turma;
import components.TurmaSistema;

import javax.swing.*;
import java.util.Map;

public class TelaConsultarNotas extends JFrame {
    private TurmaSistema sistema;

    public TelaConsultarNotas(TurmaSistema sistema) {
        this.sistema = sistema;
        setTitle("Consultar Notas");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblNomeTurma = new JLabel("Nome da Turma:");
        JTextField txtNomeTurma = new JTextField(20);
        JLabel lblMatricula = new JLabel("Matrícula do Aluno:");
        JTextField txtMatricula = new JTextField(20);
        JButton btnConsultar = new JButton("Consultar");
        JButton btnVoltar = new JButton("Voltar");

        btnConsultar.addActionListener(e -> {
            String nomeTurma = txtNomeTurma.getText().trim();
            String matricula = txtMatricula.getText().trim();

            if (nomeTurma.isEmpty() || matricula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
                return;
            }

            try {
                Turma turma = sistema.buscarTurma(nomeTurma);
                Aluno aluno = turma.buscarAluno(matricula);

                Map<String, Double> notas = aluno.getTodasNotas();
                if (notas.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Este aluno não possui notas cadastradas.");
                    return;
                }

                StringBuilder sb = new StringBuilder("Notas do aluno:\n");
                for (Map.Entry<String, Double> entry : notas.entrySet()) {
                    sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                }

                JOptionPane.showMessageDialog(this, sb.toString());

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnVoltar.addActionListener(e -> {
            new AreaAluno(sistema).setVisible(true);
            this.dispose();
        });

        JPanel panel = new JPanel();
        panel.add(lblNomeTurma);
        panel.add(txtNomeTurma);
        panel.add(lblMatricula);
        panel.add(txtMatricula);
        panel.add(btnConsultar);
        panel.add(btnVoltar);

        add(panel);
    }
}
