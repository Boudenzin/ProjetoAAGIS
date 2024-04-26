import java.util.Objects;
public class Aluno {

    private String nome;
    private String matricula;
    private String curso;


    public Aluno(String nome, String matricula, String curso) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluno aluno)) return false;
        return Objects.equals(matricula, aluno.matricula);
    }


    @Override
    public int hashCode() {
        return Objects.hash(nome, matricula, curso);
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return this.curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }



    public String toString(){
        return String.format("""
                Aluno: %s   Matricula: %s   Curso: %s
                """, this.nome, this.matricula, this.curso);
    }
}