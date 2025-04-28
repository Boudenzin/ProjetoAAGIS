import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Turma implements Serializable {

    private String nome;
    private Professor professor;
    private List<Aluno> alunos;

    public Turma(String nome, Professor professor) {
        this.nome = nome;
        this.alunos = new ArrayList<>();
        this.professor = professor;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Turma turma = (Turma) o;
        return Objects.equals(nome, turma.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }


    public void addAluno(Aluno aluno) throws AlunoJaCadastradoException {
        if (!alunos.contains(aluno)) {
            alunos.add(aluno);
        } else {
            throw new AlunoJaCadastradoException("Aluno " + aluno.getNome() + " já está na turma " + nome);
        }
    }

    public void removeAluno(Aluno aluno) {
        alunos.remove(aluno);
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
    public Professor getProfessor() {
        return professor;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


}