package gui;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.TurmaDAO;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;

public class TelaInicial extends JFrame {

    private final UsuarioService usuarioService;
    private final TurmaService turmaService;

    public TelaInicial() {
        AlunoDAO alunoDAO = new AlunoDAO();
        ProfessorDAO professorDAO = new ProfessorDAO();
        TurmaDAO turmaDAO = new TurmaDAO();
        this.usuarioService = new UsuarioService(alunoDAO, professorDAO);
        this.turmaService = new TurmaService(turmaDAO);

        //CASO ESQUEÇA: USUARIO: bouden, senha: 1234567890
        //CASO ESQUEÇA: USUARIO: vanessa.dcx, senha: 12345

        setTitle("Sistema Escolar");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = getjPanel();

        add(panel);
    }

    private JPanel getjPanel() {
        JButton btnProfessor = new JButton("Área do Professor");
        JButton btnAluno = new JButton("Área do Aluno");

        btnProfessor.addActionListener(e -> {
            new TelaLogin(usuarioService, turmaService, "professor").setVisible(true);
            dispose();
        });

        btnAluno.addActionListener(e -> {
            new TelaLogin(usuarioService, turmaService, "aluno").setVisible(true);
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(btnProfessor);
        panel.add(btnAluno);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }
}
