package gui;
import javax.swing.*;
import components.TurmaSistema;
import components.TurmaList;
public class AreaProfessor extends JFrame {
    private TurmaSistema sistema = new TurmaList();

    public AreaProfessor() {
        setTitle("Área do Professor");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnCadastrarTurma = new JButton("Cadastrar Turma");
        JButton btnConsultarTurma = new JButton("Consultar Turma");
        JButton btnCadastrarAluno = new JButton("Cadastrar Aluno");
        JButton btnConsultarAluno = new JButton("Consultar Aluno");
        JButton btnListarAlunos = new JButton("Listar Alunos da Turma");
        JButton btnCadastrarNota = new JButton("Cadastrar Nota");
        JButton btnCadastrarFaltas = new JButton("Cadastrar Faltas");
        JButton btnVoltar = new JButton("Voltar");

        //TODO: IMPLEMENTAR AS CLASSES EM VERMELHO

        btnCadastrarTurma.addActionListener(e -> {
            new TelaCadastroTurma(sistema).setVisible(true);
            dispose();
        });

        btnConsultarTurma.addActionListener(e -> {
            new TelaConsultaTurma(sistema).setVisible(true);
            dispose();
        });

        btnCadastrarAluno.addActionListener(e -> {
            new TelaCadastroAluno(sistema).setVisible(true);
            dispose();
        });

        btnConsultarAluno.addActionListener(e -> {
            new TelaConsultaAluno(sistema).setVisible(true);
            dispose();
        });

        btnListarAlunos.addActionListener(e -> {
            new TelaListarAlunos(sistema).setVisible(true);
            dispose();
        });

        btnCadastrarNota.addActionListener(e -> {
            new TelaCadastroNota(sistema).setVisible(true);
            dispose();
        });

        btnCadastrarFaltas.addActionListener(e -> {
            new TelaCadastroFaltas(sistema).setVisible(true);
            dispose();
        });



        btnVoltar.addActionListener(e -> {
            new TelaInicial().setVisible(true);
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(btnCadastrarTurma);
        panel.add(btnConsultarTurma);
        panel.add(btnCadastrarAluno);
        panel.add(btnConsultarAluno);
        panel.add(btnListarAlunos);
        panel.add(btnCadastrarNota);
        panel.add(btnCadastrarFaltas);
        panel.add(btnVoltar);

        add(panel);



    }
}
