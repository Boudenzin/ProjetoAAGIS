package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Turma implements Serializable {
    private String nome;
    private Professor professor;
    private List<AlunoTurma> participantes;

    public Turma(String nome, Professor professor) {
        this.nome = nome;
        this.professor = professor;
        this.participantes = new ArrayList<>();
    }

    public void adicionarAluno(Aluno aluno, String nomeTurma) {
        participantes.add(new AlunoTurma(aluno, nomeTurma));
    }

    public List<AlunoTurma> getParticipantes() {
        return participantes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public void setParticipantes(List<AlunoTurma> participantes) {
        this.participantes = participantes;
    }
    @Override
    public String toString() {
        return nome + " - " + professor.getNome();
    }

}
