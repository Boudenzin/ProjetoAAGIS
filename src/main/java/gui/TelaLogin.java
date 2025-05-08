package gui;

import model.Aluno;
import model.Professor;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;

public class TelaLogin extends JFrame {

    private UsuarioService usuarioService;
    private TurmaService turmaService;
    private String tipoUsuario;

    public TelaLogin(UsuarioService usuarioService, TurmaService turmaService, String tipoUsuario) {
        this.usuarioService = usuarioService;
        this.turmaService = turmaService;
        this.tipoUsuario = tipoUsuario;

        setTitle("Login - " + tipoUsuario.toUpperCase());
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JTextField usuarioField = new JTextField(15);
        JPasswordField senhaField = new JPasswordField(15);
        JButton btnLogin = new JButton("Entrar");
        JButton btnCadastro = new JButton("Fazer Cadastro");

        panel.add(new JLabel("Usuário:"));
        panel.add(usuarioField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);
        panel.add(btnLogin);
        panel.add(btnCadastro);

        btnLogin.addActionListener(e -> {
            String usuario = usuarioField.getText();
            String senha = new String(senhaField.getPassword());

            if (tipoUsuario.equalsIgnoreCase("aluno")) {
                Aluno aluno = usuarioService.autenticarAluno(usuario, senha);
                if (aluno != null) {
                    JOptionPane.showMessageDialog(this, "Bem-vindo, " + aluno.getNome());
                    new TelaAluno(usuarioService, turmaService, aluno).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciais inválidas para aluno.");
                }
            } else if (tipoUsuario.equalsIgnoreCase("professor")) {
                Professor prof = usuarioService.autenticarProfessor(usuario, senha);
                if (prof != null) {
                    JOptionPane.showMessageDialog(this, "Bem-vindo, Prof. " + prof.getNome());
                    new TelaProfessor(usuarioService, turmaService, prof).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Credenciais inválidas para professor.");
                }
            }
        });

        btnCadastro.addActionListener(e -> {
            new TelaCadastro(usuarioService, turmaService, tipoUsuario).setVisible(true);
            dispose();
        });

        add(panel);
    }
}
