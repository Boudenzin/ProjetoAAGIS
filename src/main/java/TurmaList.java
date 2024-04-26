import java.util.ArrayList;
import java.util.List;
public class TurmaList implements TurmaInterface {

    private List<Turma> turmas;
    GravadorDeDados gravarTurmas;

    public TurmaList(){
        turmas = new ArrayList<>();
        this.gravadorDeDados = new GravadorDeDados;
    }

    public void cadastrarNovaTurma(Turma turma) throws TurmaJaCriadaException {
        if (!turmas.contains(turma)) {
            turmas.add(turma);
        } else {
            throw new TurmaJaCriadaException("Turma já cadastrada no sistema com essas credenciais");
        }
    }

    public void cadastrarAlunoNaTurma(Aluno aluno, String nomeTurma) throws AlunoJaCadastradoException, TurmaNaoEncontradaException {
        Turma turma = null;
        for (Turma t : turmas) {
            if (t.getNome().equalsIgnoreCase(nomeTurma)) {
                turma = t;
                break;
            }
        }
        if (turma == null){
            throw new TurmaNaoEncontradaException("Turma de nome "+ nomeTurma +" não encontrada.");
        }

        if (turma.getAlunos().contains(aluno)){
            throw new AlunoJaCadastradoException("Aluno de matricula '"+ aluno.getMatricula() +"' já cadastrado na turma '"+ nomeTurma +"'");
        }

        turma.addAluno(aluno);
    }

    public void removerAlunoDaTurma(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException{
        Turma turma = null;
        Aluno aluno = null;
        for (Turma t : turmas) {
            if (t.getNome().equalsIgnoreCase(nomeTurma)) {
                turma = t;
                break;
            }
        }
        if (turma == null){
            throw new TurmaNaoEncontradaException("Turma de nome '"+ nomeTurma +"' não encontrada.");
        }

        for (Aluno a: turma.getAlunos()){
            if (a.getMatricula().equals(matricula)){
                aluno = a;
            }
        }

        if (aluno == null){
            throw new AlunoNaoEncontradoException("Aluno de matrícula '"+matricula+"' não encontrado na Turma "+nomeTurma);
        }

        turma.removeAluno(aluno);
    }

    public void alterarNotasDoAluno(Aluno aluno, double n1, double n2, double n3){
        aluno.setNota1(n1);
        aluno.setNota2(n2);
        aluno.setNota3(n3);
    }

    public Aluno buscarAlunoPorMatricula(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException {
        Turma turma = null;
        for (Turma t : turmas) {
            if (t.getNome().equalsIgnoreCase(nomeTurma)) {
                turma = t;
                break;
            }
        }
        if (turma == null){
            throw new TurmaNaoEncontradaException("Turma de nome '"+ nomeTurma +"' não encontrada.");
        }

        for (Aluno a: turma.getAlunos()){
            if (a.getMatricula().equals(matricula)){
                return a;
            }
        }

        throw new AlunoNaoEncontradoException("Aluno de matrícula '"+matricula+"' não encontrado");

    }

    public List<Aluno> listarAlunosDaTurma(String nomeTurma) throws TurmaNaoEncontradaException {
        Turma turma = null;
        for (Turma t: turmas){
            if (t.getNome().equalsIgnoreCase(nomeTurma)){
                turma = t;
            }
        }

        if (turma == null){
            throw new TurmaNaoEncontradaException("Turma de nome '"+ nomeTurma +"' não encontrada.");
        } else {
            return turma.getAlunos();
        }
    }
}
