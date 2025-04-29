package gui;

import components.TurmaList;
import components.TurmaSistema;

import javax.swing.*;

public class AreaAluno extends JFrame {
    private TurmaSistema sistema = new TurmaList();

    public AreaAluno() {
       setSize(400, 300);
       setTitle("Área do Aluno");
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       setLocationRelativeTo(null);

       JButton btnVerTurmas = new JButton("Ver Turmas");
       JButton btnConsultarNotas = new JButton("Consultar Notas");
       JButton btnConsultarFaltas = new JButton("Consultar Faltas");
       JButton btnVoltar = new JButton("Voltar");

       btnVerTurmas.addActionListener(e -> {
           new TelaVerTurmas(sistema).setVisible(true);
           dispose();
       });

       btnConsultarNotas.addActionListener(e -> {
           new TelaConsultarNotas(sistema).setVisible(true);
           dispose();
       });

       btnConsultarFaltas.addActionListener(e -> {
           new TelaConsultarFaltas(sistema).setVisible(true);
           dispose();
       });

       btnVoltar.addActionListener(e -> {
           new TelaInicial().setVisible(true);
           dispose();
       });

       JPanel panel = new JPanel();
       panel.add(btnVerTurmas);
       panel.add(btnConsultarNotas);
       panel.add(btnConsultarFaltas);
       panel.add(btnVoltar);
    }
}
