package components;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TurmaList implements TurmaSistema {

    private List<Turma> turmas;
    private static final String ARQUIVO_TURMAS = "turmas.dat";
    private GravadorInterface<Turma> gravador;

    public TurmaList() {
        this.turmas = new ArrayList<>();
        this.gravador = new GravadorDeDados<>();
    }

    @Override
    public Turma buscarTurma(String nomeTurma) throws TurmaNaoEncontradaException {
        return turmas.stream()
                .filter(t -> t.getNome().equalsIgnoreCase(nomeTurma))
                .findFirst()
                .orElseThrow(() -> new TurmaNaoEncontradaException("components.Turma '" + nomeTurma + "' não encontrada"));
    }

    @Override
    public Aluno buscarAluno(String matricula, Turma turma) throws AlunoNaoEncontradoException {
        return turma.getAlunos().stream()
                .filter(a -> a.getMatricula().equals(matricula))
                .findFirst()
                .orElseThrow(() -> new AlunoNaoEncontradoException("components.Aluno de matrícula '" + matricula + "' não encontrado"));
    }

    public void cadastrarNovaTurma(Turma turma) throws TurmaJaCriadaException {
        if (turmas.contains(turma)) {
            throw new TurmaJaCriadaException("components.Turma '" + turma.getNome() + "' já cadastrada.");
        }
        turmas.add(turma);
    }

    @Override
    public void removerTurma(String nomeTurma) throws TurmaNaoEncontradaException {
        Turma turma = buscarTurma(nomeTurma);
        turmas.remove(turma);
    }

    @Override
    public void cadastrarAlunoNaTurma(Aluno aluno, String nomeTurma) throws AlunoJaCadastradoException, TurmaNaoEncontradaException {
        if (aluno == null || aluno.getNome() == null || aluno.getNome().isEmpty()
                || aluno.getMatricula() == null || aluno.getMatricula().isEmpty()
                || aluno.getCurso() == null || aluno.getCurso().isEmpty()) {
            throw new IllegalArgumentException("Dados do aluno inválidos.");
        }

        Turma turma = buscarTurma(nomeTurma);
        turma.addAluno(aluno);

        try {
            gravaTurmas();  // Aqui salva todas as turmas + seus alunos no arquivo
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao salvar turmas: " + e.getMessage());
        }
    }


    @Override
    public void removerAlunoDaTurma(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException {
        Turma turma = buscarTurma(nomeTurma);
        Aluno aluno = buscarAluno(matricula, turma);
        turma.removeAluno(aluno);
    }

    @Override
    public Aluno buscarAlunoPorMatricula(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException {
        Turma turma = buscarTurma(nomeTurma);
        return buscarAluno(matricula, turma);
    }

    @Override
    public List<Aluno> listarAlunosDaTurma(String nomeTurma) throws TurmaNaoEncontradaException {
        Turma turma = buscarTurma(nomeTurma);
        return turma.getAlunos();
    }

    @Override
    public List<Turma> recuperaTurma() throws IOException {
        try {
            this.turmas = gravador.recuperar(ARQUIVO_TURMAS);
        } catch (ClassNotFoundException e) {
            throw new IOException("Erro ao ler turmas do arquivo.", e);
        }
        return turmas;
    }


    public void gravaTurmas() throws IOException {
        gravador.gravar(turmas, ARQUIVO_TURMAS);
    }
}
