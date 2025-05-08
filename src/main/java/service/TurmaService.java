package service;

import dao.TurmaDAO;
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

    public void criarTurma(String nome, Professor professor) throws IOException {
        Turma nova = new Turma(nome, professor);
        turmaDAO.salvar(nova);
    }

    public void removerTurma(String nomeTurma) throws IOException {
        turmaDAO.remover(nomeTurma);
    }

    public void adicionarAluno(String nomeTurma, Aluno aluno) throws IOException {
        Turma turma = turmaDAO.buscar(nomeTurma);
        turma.adicionarAluno(aluno, nomeTurma);
        turmaDAO.salvar(turma);
    }

    public void removerAluno(String nomeTurma, String matriculaAluno) throws IOException {
        Turma turma = turmaDAO.buscar(nomeTurma);
        turma.getParticipantes().removeIf(p -> p.getAluno().getMatricula().equals(matriculaAluno));
        turmaDAO.salvar(turma);
    }

    public void atualizarNota(String nomeTurma, String matriculaAluno, Integer unidade, double novaNota) throws IOException {
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
