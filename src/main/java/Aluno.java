import java.util.Objects;
import java.util.List;
public class Aluno {

    private String nome;
    private String matricula;
    private String curso;
    private String campus;
    private String periodo;
    private String cra;
    private List<Cadeira> cadeiras;

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
}
