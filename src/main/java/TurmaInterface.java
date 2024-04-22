import java.util.List;
public interface TurmaInterface {
    public void cadastrarNovaTurma(Turma turma) throws TurmaJaCriadaException;

    public void cadastrarAlunoNaTurma(Aluno aluno, String nomeTurma);

   /* public Aluno buscarAlunoPorMatricula(String matricula, String nomeTurma);

    public boolean verificarExistenciaAluno(String matricula, String nomeTurma);

    public List<Aluno> listarAlunosDaTurma(String nomeTurma);


    */
}