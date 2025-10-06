package gui;

import service.TurmaService;
import service.UsuarioService;
import model.Aluno;
import model.Professor;

import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {

    private UsuarioService usuarioService;
    private String tipoUsuario;
    private TurmaService turmaService;

    public TelaCadastro(UsuarioService usuarioService, TurmaService turmaService, String tipoUsuario) {
        this.usuarioService = usuarioService;
        this.tipoUsuario = tipoUsuario;
        this.turmaService = turmaService;

        setTitle("Cadastro de " + tipoUsuario.substring(0, 1).toUpperCase() + tipoUsuario.substring(1));
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));

        JTextField nomeField = new JTextField();
        JTextField matriculaField = new JTextField();
        JTextField usuarioField = new JTextField();
        JPasswordField senhaField = new JPasswordField();
        JTextField extraField = new JTextField();

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Matrícula:"));
        panel.add(matriculaField);
        panel.add(new JLabel("Usuário:"));
        panel.add(usuarioField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);

        if (tipoUsuario.equalsIgnoreCase("aluno")) {
            panel.add(new JLabel("Curso:"));
            panel.add(extraField);
        } else if (tipoUsuario.equalsIgnoreCase("professor")) {
            panel.add(new JLabel("Departamento:"));
            panel.add(extraField);
        }

        JButton btnCadastrar = new JButton("Cadastrar");

        btnCadastrar.addActionListener(e -> {
            String nome = nomeField.getText();
            String matricula = matriculaField.getText();
            String usuario = usuarioField.getText();
            String senha = new String(senhaField.getPassword());
            String extra = extraField.getText();

            if (nome.isBlank() || matricula.isBlank() || usuario.isBlank() || senha.isBlank() || extra.isBlank()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                if (tipoUsuario.equalsIgnoreCase("aluno")) {
                    Aluno aluno = new Aluno(nome, matricula, extra, usuario, senha);
                    usuarioService.cadastrarAluno(aluno);
                } else if (tipoUsuario.equalsIgnoreCase("professor")) {
                    Professor professor = new Professor(nome, matricula, extra, usuario, senha);
                    usuarioService.cadastrarProfessor(professor);
                }

                JOptionPane.showMessageDialog(this, "Cadastro realizado com sucesso!");
                new TelaLogin(usuarioService, turmaService, tipoUsuario).setVisible(true);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage());
            }
        });

        JPanel container = new JPanel(new BorderLayout());
        container.add(panel, BorderLayout.CENTER);
        container.add(btnCadastrar, BorderLayout.SOUTH);
        add(container);
    }
}
