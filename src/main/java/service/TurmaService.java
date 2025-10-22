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
            throw new TurmaJaCriadaException("Turma com o nome '" + nome + "' já existe.");
        }

        Turma nova = new Turma(nome, professor);
        turmaDAO.salvar(nova);
    }

    /**
     * Remove uma turma, mas APENAS se o professor logado for o dono da turma.
     * Utiliza o método auxiliar buscarEAutorizar para garantir a segurança.
     *
     * @param nomeTurma Nome da turma a ser removida.
     * @param professorLogado O professor que tenta executar a ação.
     * @throws IOException Em caso de erro de I/O.
     * @throws TurmaNaoEncontradaException Se a turma não existir.
     * @throws AutorizacaoException Se o professor logado não for o dono da turma.
     * @throws IllegalArgumentException Se as entradas forem nulas/vazias.
     */
    public void removerTurma(String nomeTurma, Professor professorLogado) throws IOException, TurmaNaoEncontradaException, AutorizacaoException {

        buscarEAutorizar(nomeTurma, professorLogado);

        turmaDAO.remover(nomeTurma);
    }

    public void adicionarAluno(String nomeTurma, Aluno aluno, Professor professorLogado) throws IOException, TurmaNaoEncontradaException, AlunoJaMatriculadoException, AutorizacaoException {


        if (aluno == null) {
            throw new IllegalArgumentException("O aluno não pode ser nulo.");
        }

        Turma turma = buscarEAutorizar(nomeTurma, professorLogado);

        turma.adicionarAluno(aluno);
        turmaDAO.salvar(turma);
    }

    public void removerAluno(String nomeTurma, String matriculaAluno, Professor professorLogado) throws IOException, TurmaNaoEncontradaException, AutorizacaoException, AlunoNaoEncontradoException {

        if (matriculaAluno == null || matriculaAluno.isBlank()) {
            throw new IllegalArgumentException("A matrícula do aluno não pode ser nula ou vazia.");
        }

        Turma turma = buscarEAutorizar(nomeTurma, professorLogado);

        turma.removerAlunoPorMatricula(matriculaAluno);
        turmaDAO.salvar(turma);
    }

    public void atualizarNota(String nomeTurma, String matriculaAluno, Integer unidade, double novaNota, Professor professorLogado) throws IOException, NotaInvalidaException, UnidadeInvalidaException, TurmaNaoEncontradaException, AutorizacaoException, AlunoNaoEncontradoException {

        if (unidade < 1 || unidade > 4) {
            throw new UnidadeInvalidaException("A unidade deve estar entre 1 e 4.");
        }
        if (novaNota < 0 || novaNota > 10) {
            throw new NotaInvalidaException("A nota deve estar entre 0 e 10.");
        }
        if (matriculaAluno == null || matriculaAluno.isBlank()) {
            throw new IllegalArgumentException("A matrícula do aluno não pode ser nula ou vazia.");
        }

        Turma turma = buscarEAutorizar(nomeTurma, professorLogado);

        turma.atualizarNotaDoAluno(matriculaAluno, unidade, novaNota);

        turmaDAO.salvar(turma);
    }

    public void atualizarFaltas(String nomeTurma, String matriculaAluno, int novasFaltas, Professor professorLogado) throws IOException, AutorizacaoException, TurmaNaoEncontradaException, AlunoNaoEncontradoException {

        if (novasFaltas < 0) {
            throw new IllegalArgumentException("O número de faltas não pode ser negativo.");
        }
        if (matriculaAluno == null || matriculaAluno.isBlank()) {
            throw new IllegalArgumentException("A matrícula do aluno não pode ser nula ou vazia.");
        }


        Turma turma = buscarEAutorizar(nomeTurma, professorLogado);

        turma.atualizarFaltasDoAluno(matriculaAluno, novasFaltas);

        turmaDAO.salvar(turma);
    }

    public List<AlunoTurma> listarAlunos(String nomeTurma, Professor professorLogado) throws IOException, TurmaNaoEncontradaException, AutorizacaoException {


        Turma turma = buscarEAutorizar(nomeTurma, professorLogado);

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


    /**
     * Metodo auxiliar privado para buscar uma turma, validar a sua existência
     * e garantir que o professor logado é o dono.
     * @param nomeTurma Nome da turma a ser buscada.
     * @param professorLogado Professor que tenta a ação.
     * @return A Turma, se for encontrada e autorizada.
     * @throws TurmaNaoEncontradaException Se a turma não existir.
     * @throws AutorizacaoException Se o professor não for o dono.
     * @throws IllegalArgumentException Se as entradas forem nulas/vazias.
     */

    private Turma buscarEAutorizar(String nomeTurma, Professor professorLogado)
            throws TurmaNaoEncontradaException, AutorizacaoException, IllegalArgumentException {

        if (nomeTurma == null || nomeTurma.isBlank()) {
            throw new IllegalArgumentException("O nome da turma não pode ser nulo ou vazio.");
        }
        if (professorLogado == null) {
            throw new IllegalArgumentException("Professor logado não pode ser nulo.");
        }

        Turma turma;
        try {
            turma = turmaDAO.buscar(nomeTurma);
        } catch (IOException e) {
            throw new PersistenciaException("Erro de persistência ao buscar turma: " + e.getMessage(), e);
        }

        if (turma == null) {
            throw new TurmaNaoEncontradaException("A turma '" + nomeTurma + "' não foi encontrada.");
        }

        if (!turma.getProfessor().getUsuario().equals(professorLogado.getUsuario())) {
            throw new AutorizacaoException("Permissão negada. Você não é o professor desta turma.");
        }

        return turma;
    }



}
