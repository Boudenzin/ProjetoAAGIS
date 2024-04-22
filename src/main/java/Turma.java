import java.util.List;
import java.util.ArrayList;
public class Turma {

    private String nome;
    private String docente;
    private List<Aluno> alunos;

    public Turma(String nome, String docente) {
        this.nome = nome;
        this.alunos = new ArrayList<>();
        this.docente = docente;
    }

    public void addAluno(Aluno aluno) {
        if (!alunos.contains(aluno)) {
            alunos.add(aluno);
            System.out.println("Aluno cadastrado com sucesso");
        } else {
            System.out.println("Aluno " + aluno.getNome() + " já está na turma " + nome);
        }
    }

    public void removeAluno(Aluno aluno) {
        if (alunos.contains(aluno)) {
            alunos.remove(aluno);
        } else {
            System.out.println("Aluno " + aluno.getNome() + " não está na turma " + nome);
        }
    }

    public Aluno getAluno(String nome) throws AlunoNaoEncontradoException{
        for (Aluno aluno : alunos) {
            if (aluno.getNome().equals(nome)) {
                return aluno;
            }
        }
        throw new AlunoNaoEncontradoException("Aluno " + nome + " não encontrado na turma " + this.nome);
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }
    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}

