package gui;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import dao.TurmaDAO;
import service.TurmaService;
import service.UsuarioService;

import javax.swing.*;

public class TelaInicial extends JFrame {

    private UsuarioService usuarioService;
    private TurmaService turmaService;
    private AlunoDAO alunoDAO;
    private ProfessorDAO professorDAO;
    private TurmaDAO turmaDAO;

    public TelaInicial() {
        this.alunoDAO = new AlunoDAO();
        this.professorDAO = new ProfessorDAO();
        this.turmaDAO = new TurmaDAO();
        this.usuarioService = new UsuarioService(alunoDAO, professorDAO);
        this.turmaService = new TurmaService(turmaDAO);

        setTitle("Sistema Escolar");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }
}
