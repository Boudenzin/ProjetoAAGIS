import java.io.IOException;
import java.util.List;
public interface TurmaSistema {
    public void cadastrarNovaTurma(Turma turma) throws TurmaJaCriadaException, NullPointerException;

    public Turma buscarTurma(String nomeTurma) throws TurmaNaoEncontradaException;

    public Aluno buscarAluno(String matricula, Turma turma) throws AlunoNaoEncontradoException;

    public void removerTurma(String nomeTurma) throws TurmaNaoEncontradaException;

    public void cadastrarAlunoNaTurma(Aluno aluno, String nomeTurma) throws AlunoJaCadastradoException, TurmaNaoEncontradaException, NullPointerException;

    public void removerAlunoDaTurma(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException;

    public Aluno buscarAlunoPorMatricula(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException;

    public List<Aluno> listarAlunosDaTurma(String nomeTurma) throws TurmaNaoEncontradaException;

    public List<Turma> recuperaTurma() throws IOException;

    public void gravaTurmas() throws IOException;


}