package model;

import exceptions.AlunoJaMatriculadoException;

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

    public void adicionarAluno(Aluno aluno) throws AlunoJaMatriculadoException {

        boolean jaMatriculado = this.participantes.stream()
                .anyMatch(alunoTurma -> alunoTurma.getAluno().equals(aluno));

        if (jaMatriculado) {
            throw new AlunoJaMatriculadoException("O aluno " + aluno.getNome() + " já está matriculado nesta turma.");
        }

        AlunoTurma novoAlunoTurma = new AlunoTurma(aluno, this.nome); // Usando o nome da própria turma
        this.participantes.add(novoAlunoTurma);
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
