import java.util.List;
public interface TurmaInterface {
    public void cadastrarNovaTurma(Turma turma) throws TurmaJaCriadaException;

    public void cadastrarAlunoNaTurma(Aluno aluno, String nomeTurma) throws AlunoJaCadastradoException, TurmaNaoEncontradaException;

    public void removerAlunoDaTurma(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException;

    public Aluno buscarAlunoPorMatricula(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException;

    public List<Aluno> listarAlunosDaTurma(String nomeTurma) throws TurmaNaoEncontradaException;

    //public boolean verificarExistenciaAluno(String matricula, String nomeTurma);
}