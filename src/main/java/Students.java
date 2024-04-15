import java.util.Objects;
import java.util.List;
public class Students {

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
        Students students = (Students) o;
        return Objects.equals(matricula, students.matricula) && Objects.equals(curso, students.curso) && Objects.equals(campus, students.campus) && Objects.equals(periodo, students.periodo) && Objects.equals(cra, students.cra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula, curso, campus, periodo, cra);
    }
}
