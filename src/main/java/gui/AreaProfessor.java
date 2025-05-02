package gui;
import javax.swing.*;
import components.TurmaSistema;
public class AreaProfessor extends JFrame {

    public AreaProfessor(TurmaSistema sistema) {
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
        JButton btnCadastrarPresenca = new JButton("Cadastrar Faltas");
        JButton btnVoltar = new JButton("Voltar");

        //TODO: IMPLEMENTAR AS CLASSES EM VERMELHO

        btnCadastrarTurma.addActionListener(e -> {
            new TelaCadastroTurma(sistema).setVisible(true);
            this.setVisible(false);
        });

        btnConsultarTurma.addActionListener(e -> {
            new TelaConsultaTurma(sistema).setVisible(true);
            this.setVisible(false);
        });

        btnCadastrarAluno.addActionListener(e -> {
            new TelaCadastroAluno(sistema).setVisible(true);
            this.setVisible(false);
        });

        btnConsultarAluno.addActionListener(e -> {
            new TelaConsultaAluno(sistema).setVisible(true);
            this.setVisible(false);
        });

        btnListarAlunos.addActionListener(e -> {
            new TelaListarAlunos(sistema).setVisible(true);
            this.setVisible(false);
        });

        btnCadastrarNota.addActionListener(e -> {
            new TelaCadastroNota(sistema).setVisible(true);
            this.setVisible(false);
        });

        btnCadastrarPresenca.addActionListener(e -> {
            new TelaCadastroPresenca(sistema).setVisible(true);
            this.setVisible(false);
        });

        btnVoltar.addActionListener(e -> {
            new TelaInicial(sistema).setVisible(true);
            dispose();
        });

        JPanel panel = new JPanel();
        panel.add(btnCadastrarTurma);
        panel.add(btnConsultarTurma);
        panel.add(btnCadastrarAluno);
        panel.add(btnConsultarAluno);
        panel.add(btnListarAlunos);
        panel.add(btnCadastrarNota);
        panel.add(btnCadastrarPresenca);
        panel.add(btnVoltar);

        add(panel);



    }
}
