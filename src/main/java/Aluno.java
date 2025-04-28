import java.io.Serializable;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;
public class Aluno implements Serializable {

    private String nome;
    private String matricula;
    private String curso;
    private Map <String, Double> notas;
    private Map <String, Integer> frequencias;


    public Aluno(String nome, String matricula, String curso) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.notas = new HashMap<>();
        this.frequencias = new HashMap<>();
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

    public void adicionarNota(String disciplina, double nota) {
        notas.put(disciplina, nota);
    }

    public double getNota(String disciplina) {
        return notas.getOrDefault(disciplina, 0.0);
    }

    public int getFrequencia(String disciplina) {
        return frequencias.getOrDefault(disciplina, 0);
    }
    public void registrarFrequencia(String disciplina, int frequencia) {
        frequencias.put(disciplina, frequencias.getOrDefault(disciplina, 0) + 1);
    }

    public String toString(){
        return String.format("""
                Aluno: %s   Matricula: %s   Curso: %s
                """, this.nome, this.matricula, this.curso);
    }
}