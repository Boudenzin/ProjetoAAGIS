import java.util.ArrayList;
import java.util.Objects;
import java.util.List;
public class Aluno {

    private String nome;
    private String matricula;
    private String curso;
    private String campus;
    private String periodo;
    private double cra;
    private List<Cadeira> cadeiras;

    public Aluno(String nome, String matricula, String curso, String campus, String periodo, double cra) {
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.campus = campus;
        this.periodo = periodo;
        this.cra = cra;
        this.cadeiras = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(matricula, aluno.matricula) && Objects.equals(curso, aluno.curso) && Objects.equals(campus, aluno.campus) && Objects.equals(periodo, aluno.periodo) && Objects.equals(cra, aluno.cra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula, curso, campus, periodo, cra);
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

    public String getCampus() {
        return this.campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getPeriodo() {
        return this.periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public double getCra() {
        return this.cra;
    }

    public void setCra(double cra) {
        this.cra = cra;
    }

    public List<Cadeira> getCadeiras() {
        return this.cadeiras;
    }

    public void setCadeiras(List<Cadeira> cadeiras) {
        this.cadeiras = cadeiras;
    }
}
