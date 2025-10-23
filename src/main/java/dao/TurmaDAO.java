package dao;

import model.Aluno;
import model.Professor;
import model.Turma;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TurmaDAO {

    private static final String ARQUIVO_TURMAS = "turmas.dat";
    private GravadorDeDados<Turma> gravador;
    private List<Turma> turmas;

    public TurmaDAO() {
        this.gravador = new GravadorDeDados<>();
        this.turmas = new ArrayList<>();

        try {
            this.turmas = gravador.recuperar(ARQUIVO_TURMAS);
        } catch (Exception e) {
            System.out.println("Nenhuma turma carregada: " + e.getMessage());
        }
    }

    public void salvar(Turma turma) throws IOException {
        remover(turma.getNome());  // Evita duplicação
        turmas.add(turma);
        gravador.gravar(turmas, ARQUIVO_TURMAS);
    }

    public void remover(String nomeTurma) throws IOException {
        turmas.removeIf(t -> t.getNome().equalsIgnoreCase(nomeTurma));
        gravador.gravar(turmas, ARQUIVO_TURMAS);
    }

    public Turma buscar(String nomeTurma) throws IOException {
        return turmas.stream()
                .filter(t -> t.getNome().equalsIgnoreCase(nomeTurma))
                .findFirst()
                .orElse(null);
    }

    public List<Turma> listar() {
        return new ArrayList<>(turmas); // Retorna cópia para evitar manipulação externa
    }

    public List<Turma> listarPorProfessor(Professor professor) {
        List<Turma> turmas = this.listar();
        List<Turma> turmasDoProfessor = new ArrayList<>();

        for (Turma turma : turmas) {
            if (turma.getProfessor().equals(professor)) {
                turmasDoProfessor.add(turma);
            }
        }

        return turmasDoProfessor;
    }

    public List<Turma> listarPorAluno(Aluno aluno) {
        List<Turma> turmas = this.listar();
        List<Turma> turmasDoAluno = new ArrayList<>();

        for (Turma turma : turmas) {
            boolean alunoEstaNaTurma = turma.getParticipantes().stream()
                    .anyMatch(alunoTurma -> alunoTurma.getAluno().equals(aluno));

            if (alunoEstaNaTurma) {

                turmasDoAluno.add(turma);
            }
        }
        return turmasDoAluno;
    }
}

