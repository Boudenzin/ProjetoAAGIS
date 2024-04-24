import java.util.Objects;
public class Aluno {

    private String nome;
    private double media;
    private double nota1;
    private double nota2;
    private double nota3;
    private String matricula;
    private String curso;


    public Aluno(String nome, String matricula, String curso) {
        this.nome = nome;
        this.nota1 = 0.0;
        this.nota2 = 0.0;
        this.nota3 = 0.0;
        this.media = calcularMedia();
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

    public void setNota1(double nota1) {
        this.nota1 = nota1;
    }

    public void setNota2(double nota2) {
        this.nota2 = nota2;
    }

    public void setNota3(double nota3) {
        this.nota3 = nota3;
    }

    public double getNota1() {
        return this.nota1;
    }

    public double getNota2() {
        return this.nota2;
    }

    public double getNota3() {
        return this.nota3;
    }

    public double calcularMedia() {
        return (this.nota1 + this.nota2 + this.nota3)/3;
    }

    public String toString(){
        return String.format("""
                Aluno: %s   Matricula: %s   Curso: %s   /   Nota 1: %.1f    Nota 2: %.1f    Nota3: %.1f
                """, this.nome, this.matricula, this.curso, this.nota1, this.nota2, this.nota3);
    }
}