import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class TurmaList implements TurmaInterface {

    private List<Turma> turmas;
    GravarTurmas gravarTurmas;

    public TurmaList(){
        turmas = new ArrayList<>();
        this.gravarTurmas = new GravarTurmas();
    }

    public void recuperaDadosEmTurma() throws IOException {
        List<String> dadosTurma = gravarTurmas.recuperaDadosDeTurma();
        for (String dadoTurma: dadosTurma){
            String [] dados = dadoTurma.split("###");
            Turma T = new Turma(dados[0],dados[1]);
            this.turmas.add(T);
        }
    }

    public void gravaDadosEmTurma() throws IOException {
        List<String> dadosTurmas = new ArrayList<>();
        for (Turma t : this.turmas){
            String dado = t.getNome()+"###"+t.getDocente();
            dadosTurmas.add(dado);
        }
        this.gravarTurmas.gravaDadosDeTurmas(dadosTurmas);
    }


    public Turma buscarTurma(String nomeTurma) {
        for (Turma t : turmas) {
            if (t.getNome().equalsIgnoreCase(nomeTurma)) {
                return t;
            }
        }
        return null;
    }


    public Aluno buscarAluno(String matricula, Turma turma){
        for (Aluno a: turma.getAlunos()){
            if (a.getMatricula().equals(matricula)){
                return a;
            }
        }
        return null;
    }


    public void cadastrarNovaTurma(Turma turma) throws TurmaJaCriadaException {
        if (!turmas.contains(turma)) {
            turmas.add(turma);
        } else {
            throw new TurmaJaCriadaException("Turma de '"+ turma.getNome() +"' já cadastrada no sistema.");
        }
    }


    public void removerTurma(String nomeTurma) throws TurmaNaoEncontradaException{
        Turma turma = buscarTurma(nomeTurma);
        if (turma == null){
            throw new TurmaNaoEncontradaException("Turma de nome '"+ nomeTurma +"' não encontrada");
        }
        turmas.remove(turma);
    }


    public void cadastrarAlunoNaTurma(Aluno aluno, String nomeTurma) throws AlunoJaCadastradoException, TurmaNaoEncontradaException {
        Turma turma = buscarTurma(nomeTurma);
        if (turma == null){
            throw new TurmaNaoEncontradaException("Turma de nome '"+ nomeTurma +"' não encontrada.");
        }
        if (turma.getAlunos().contains(aluno)){
            throw new AlunoJaCadastradoException("Aluno de matricula '"+ aluno.getMatricula() +"' já cadastrado na turma '"+ nomeTurma +"'");
        }
        turma.addAluno(aluno);
    }


    public void removerAlunoDaTurma(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException{
        Turma turma = buscarTurma(nomeTurma);
        if (turma == null){
            throw new TurmaNaoEncontradaException("Turma de nome '"+ nomeTurma +"' não encontrada.");
        }
        Aluno aluno = buscarAluno(matricula, turma);
        if (aluno == null){
            throw new AlunoNaoEncontradoException("Aluno de matrícula '"+matricula+"' não encontrado na Turma "+nomeTurma);
        }
        turma.removeAluno(aluno);
    }


    public Aluno buscarAlunoPorMatricula(String matricula, String nomeTurma) throws AlunoNaoEncontradoException, TurmaNaoEncontradaException {
        Turma turma = buscarTurma(nomeTurma);
        if (turma == null){
            throw new TurmaNaoEncontradaException("Turma de nome '"+ nomeTurma +"' não encontrada.");
        }
        Aluno aluno = buscarAluno(matricula, turma);
        if (aluno == null){
            throw new AlunoNaoEncontradoException("Aluno de matrícula '"+matricula+"' não encontrado");
        }
        return aluno;
    }


    public List<Aluno> listarAlunosDaTurma(String nomeTurma) throws TurmaNaoEncontradaException {
        Turma turma = buscarTurma(nomeTurma);
        if (turma == null){
            throw new TurmaNaoEncontradaException("Turma de nome '"+ nomeTurma +"' não encontrada.");
        }
        return turma.getAlunos();
    }
}