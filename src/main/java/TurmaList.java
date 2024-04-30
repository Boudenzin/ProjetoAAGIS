import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class TurmaList implements TurmaSistema {

    private List<Turma> turmas;
    private static final String ARQUIVOS_ALUNOS = "alunos.txt";
    private static final String ARQUIVOS_TURMAS = "turmas.txt";
    GravadorDeDados gravador;

    public TurmaList() {
        turmas = new ArrayList<>();
        this.gravador = new GravadorDeDados();
    }


    public Turma buscarTurma(String nomeTurma) throws TurmaNaoEncontradaException {
        for (Turma t : turmas) {
            if (t.getNome().equalsIgnoreCase(nomeTurma)) {
                return t;
            }
        }
        throw new TurmaNaoEncontradaException("Turma não encontrada");
    }


    public Aluno buscarAluno(String matricula, Turma turma) throws AlunoNaoEncontradoException{
        for (Aluno a : turma.getAlunos()) {
            if (a.getMatricula().equals(matricula)) {
                return a;
            }
        }
        throw new AlunoNaoEncontradoException("Aluno não Encontrado");
    }


    public void cadastrarNovaTurma(Turma turma) throws TurmaJaCriadaException, NullPointerException {
        if (turma.getNome().isEmpty() || turma.getDocente().isEmpty()) {
            throw new NullPointerException("Entrada Inválida. Tente Novamente");
        }

        if (!turmas.contains(turma)) {
            turmas.add(turma);
        } else {
            throw new TurmaJaCriadaException("Turma de '" + turma.getNome() + "' já cadastrada no sistema.");
        }
    }


    public void removerTurma(String nomeTurma) throws TurmaNaoEncontradaException {
        try {
            Turma turma = buscarTurma(nomeTurma);
            turmas.remove(turma);
        } catch (TurmaNaoEncontradaException e) {
            throw new TurmaNaoEncontradaException(e.getMessage());
        }
    }


    public void cadastrarAlunoNaTurma(Aluno aluno, String nomeTurma) throws AlunoJaCadastradoException, TurmaNaoEncontradaException {
        try {
            Turma turma = buscarTurma(nomeTurma);

            try {
                turma.addAluno(aluno);
            } catch (AlunoJaCadastradoException e) {
                throw new AlunoJaCadastradoException(e.getMessage());
            }

        } catch (TurmaNaoEncontradaException e) {
            throw new TurmaNaoEncontradaException(e.getMessage());
        }

        try {
            this.gravaAlunos();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Dados não gravados");
        }
    }


    public void removerAlunoDaTurma(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException {
        Turma turma = buscarTurma(nomeTurma);
        if (turma == null) {
            throw new TurmaNaoEncontradaException("Turma de nome '" + nomeTurma + "' não encontrada.");
        }
        Aluno aluno = buscarAluno(matricula, turma);
        if (aluno == null) {
            throw new AlunoNaoEncontradoException("Aluno de matrícula '" + matricula + "' não encontrado na Turma " + nomeTurma);
        }
        turma.removeAluno(aluno);
    }


    public Aluno buscarAlunoPorMatricula(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException {
        Turma turma = buscarTurma(nomeTurma);
        if (turma == null) {
            throw new TurmaNaoEncontradaException("Turma de nome '" + nomeTurma + "' não encontrada.");
        }
        Aluno aluno = buscarAluno(matricula, turma);
        if (aluno == null) {
            throw new AlunoNaoEncontradoException("Aluno de matrícula '" + matricula + "' não encontrado");
        }
        return aluno;
    }


    public List<Aluno> listarAlunosDaTurma(String nomeTurma) throws TurmaNaoEncontradaException {
        Turma turma = buscarTurma(nomeTurma);
        if (turma == null) {
            throw new TurmaNaoEncontradaException("Turma de nome '" + nomeTurma + "' não encontrada.");
        }
        return turma.getAlunos();
    }


    public List<Turma> recuperaTurma() throws IOException {

        List<Turma> listaRetornadaDeTurma = new ArrayList<>();

        for (String dado : gravador.recuperaTextoDeArquivo(ARQUIVOS_TURMAS)) {
            String[] dadosTurmas = dado.split("###");

            Turma turma = new Turma(dadosTurmas[0], dadosTurmas[1]);
            listaRetornadaDeTurma.add(turma);

        }

        return listaRetornadaDeTurma;

    }
    public void recuperaAlunos() throws IOException, AlunoJaCadastradoException, TurmaNaoEncontradaException {

        List<Turma> listaRetornadaDeAluno = new ArrayList<>();

        try {
            for (String dado : gravador.recuperaTextoDeArquivo(ARQUIVOS_ALUNOS)) {
                String[] dadosAlunos = dado.split("###");

                try {
                    this.cadastrarAlunoNaTurma(new Aluno(dadosAlunos[1], dadosAlunos[2], dadosAlunos[3]), dadosAlunos[0]);
                } catch (TurmaNaoEncontradaException e) {
                    throw new TurmaNaoEncontradaException(e.getMessage());
                } catch (AlunoJaCadastradoException e) {
                    throw new AlunoJaCadastradoException(e.getMessage());
                }

            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    public void gravaTurmas() throws IOException {

        List<String> turmas = new ArrayList<>();
        String turma = "";
        for (Turma t : this.turmas) {
            turma = t.getNome() + "###" + t.getDocente();
            turmas.add(turma);
        }

        gravador.gravaTextoEmArquivo(turmas, ARQUIVOS_TURMAS);
    }

    public void gravaAlunos() throws IOException {

        List<String> alunos = new ArrayList<>();
        String aluno = "";
        for (Turma t : this.turmas) {
            for (Aluno a : t.getAlunos()) {
                aluno = t.getNome() + "###" + a.getNome() + "###" + a.getMatricula() + "###" + a.getCurso();
                alunos.add(aluno);
            }
            gravador.gravaTextoEmArquivo(alunos, ARQUIVOS_ALUNOS);
        }
    }

}