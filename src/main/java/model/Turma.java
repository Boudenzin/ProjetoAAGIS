package model;

import exceptions.AlunoJaMatriculadoException;
import exceptions.AlunoNaoEncontradoException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        return Collections.unmodifiableList(participantes);
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

    /**
     * Busca um participante (AlunoTurma) dentro da lista da turma
     * com base na matrícula do aluno.
     *
     * @param matriculaAluno A matrícula a ser buscada.
     * @return um Optional contendo o AlunoTurma se encontrado, ou Optional.empty() se não.
     */
    public Optional<AlunoTurma> getParticipantePorMatricula(String matriculaAluno) {
        if (matriculaAluno == null || matriculaAluno.isBlank()) {
            return Optional.empty();
        }

        return this.participantes.stream()
                .filter(p -> p.getAluno().getMatricula().equals(matriculaAluno))
                .findFirst();
    }

    public void atualizarNotaDoAluno(String matricula, int unidade, double nota) throws AlunoNaoEncontradoException {
        AlunoTurma at = getParticipantePorMatricula(matricula)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com matrícula " + matricula + " não encontrado nesta turma."));

        at.setNotaDaUnidade(unidade, nota);
    }

    public void atualizarFaltasDoAluno(String matricula, int faltas) throws AlunoNaoEncontradoException {

        AlunoTurma at = getParticipantePorMatricula(matricula)
                .orElseThrow(() -> new AlunoNaoEncontradoException("Aluno com matrícula " + matricula + " não encontrado nesta turma."));

        at.setFaltas(faltas);
    }

    public void removerAlunoPorMatricula(String matricula) throws AlunoNaoEncontradoException {
        boolean removido = this.participantes.removeIf(p -> p.getAluno().getMatricula().equals(matricula));

        if (!removido) {
            throw new AlunoNaoEncontradoException("Aluno com matrícula " + matricula + " não foi encontrado nessa turma.");
        }
    }


}
