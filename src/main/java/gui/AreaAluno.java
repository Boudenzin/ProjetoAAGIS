package gui;

import components.TurmaSistema;

import javax.swing.*;

public class AreaAluno extends JFrame {

    public AreaAluno(TurmaSistema sistema) {
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
           this.setVisible(false);
       });

       btnConsultarNotas.addActionListener(e -> {
           new TelaConsultarNotas(sistema).setVisible(true);
           this.setVisible(false);
       });

       btnConsultarFaltas.addActionListener(e -> {
           new TelaConsultarFaltas(sistema).setVisible(true);
           this.setVisible(false);
       });

       btnVoltar.addActionListener(e -> {
           new TelaInicial(sistema).setVisible(true);
           this.setVisible(false);
       });

       JPanel panel = new JPanel();
       panel.add(btnVerTurmas);
       panel.add(btnConsultarNotas);
       panel.add(btnConsultarFaltas);
       panel.add(btnVoltar);

       add(panel);
    }
}
