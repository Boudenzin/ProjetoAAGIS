package service;

import dao.TurmaDAO;
import exceptions.*;
import model.Aluno;
import model.AlunoTurma;
import model.Professor;
import model.Turma;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TurmaService {

    private TurmaDAO turmaDAO;

    public TurmaService(TurmaDAO turmaDAO) {
        this.turmaDAO = turmaDAO;
    }

    public void criarTurma(String nome, Professor professor) throws IOException, TurmaJaCriadaException {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da turma não pode ser nulo ou vazio.");
        }

        if (professor == null) {
            throw new IllegalArgumentException("Professor não pode ser nulo.");
        }

        if (turmaDAO.buscar(nome) != null) {
            throw new TurmaJaCriadaException("Turma com o nome '" + nome + "' já existe."); // <-- MUDANÇA AQUI
        }

        Turma nova = new Turma(nome, professor);
        turmaDAO.salvar(nova);
    }

    public void removerTurma(String nomeTurma) throws IOException {
        turmaDAO.remover(nomeTurma);
    }

    public void adicionarAluno(String nomeTurma, Aluno aluno) throws IOException, TurmaNaoEncontradaException, AlunoJaMatriculadoException {


        if (aluno == null) {
            throw new IllegalArgumentException("O aluno não pode ser nulo.");
        }

        if (nomeTurma == null || nomeTurma.isBlank()) {
            throw new IllegalArgumentException("O nome da turma não pode ser nulo ou vazio.");
        }

        Turma turma = turmaDAO.buscar(nomeTurma);

        if (turma == null) {
            throw new TurmaNaoEncontradaException("A turma '" + nomeTurma + "' não foi encontrada.");
        }

        turma.adicionarAluno(aluno);
        turmaDAO.salvar(turma);
    }

    public void removerAluno(String nomeTurma, String matriculaAluno) throws IOException {
        Turma turma = turmaDAO.buscar(nomeTurma);
        turma.getParticipantes().removeIf(p -> p.getAluno().getMatricula().equals(matriculaAluno));
        turmaDAO.salvar(turma);
    }

    public void atualizarNota(String nomeTurma, String matriculaAluno, Integer unidade, double novaNota) throws IOException, NotaInvalidaException, UnidadeInvalidaException {

        if (unidade < 1 || unidade > 4) {
            throw new UnidadeInvalidaException("A unidade deve estar entre 1 e 4.");
        }
        if (novaNota < 0 || novaNota > 10) {
            throw new NotaInvalidaException("A nota deve estar entre 0 e 10.");
        }

        Turma turma = turmaDAO.buscar(nomeTurma);
        Optional<AlunoTurma> alunoTurma = turma.getParticipantes()
                .stream()
                .filter(p -> p.getAluno().getMatricula().equals(matriculaAluno))
                .findFirst();

        alunoTurma.ifPresent(at -> at.setNotaDaUnidade(unidade, novaNota));
        turmaDAO.salvar(turma);
    }

    public void atualizarFaltas(String nomeTurma, String matriculaAluno, int novasFaltas) throws IOException {
        Turma turma = turmaDAO.buscar(nomeTurma);
        Optional<AlunoTurma> alunoTurma = turma.getParticipantes()
                .stream()
                .filter(p -> p.getAluno().getMatricula().equals(matriculaAluno))
                .findFirst();

        alunoTurma.ifPresent(at -> at.setFaltas(novasFaltas));
        turmaDAO.salvar(turma);
    }

    public List<AlunoTurma> listarAlunos(String nomeTurma) throws IOException {
        Turma turma = turmaDAO.buscar(nomeTurma);
        return turma.getParticipantes();
    }

    public List<Turma> listarTodasTurmas() throws IOException {
        return turmaDAO.listar();
    }

    public List<Turma> getTurmasDoProfessor(Professor professor) throws IOException {
        List<Turma> turmas = this.listarTodasTurmas();
        return turmas.stream()
                .filter(turma -> turma.getProfessor().getUsuario().equalsIgnoreCase(professor.getUsuario()))
                .toList();
    }


    public Turma buscarTurma(String nomeTurma) throws IOException {
        return turmaDAO.buscar(nomeTurma);
    }
    public List<Turma> getTurmasDoAluno(Aluno aluno) throws IOException {
        List<Turma> turmas = this.listarTodasTurmas();
        return turmas.stream()
                .filter(turma -> turma.getParticipantes().stream()
                        .anyMatch(alunoTurma -> alunoTurma.getAluno().equals(aluno)))
                .toList();
    }

}
