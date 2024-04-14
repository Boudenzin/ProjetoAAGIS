import java.util.Objects;

public class Students {

    private String matricula;
    private String program;
    private String campus;
    private String periodo;
    private String cra;
    private Courses cadeira;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Students students = (Students) o;
        return Objects.equals(matricula, students.matricula) && Objects.equals(program, students.program) && Objects.equals(campus, students.campus) && Objects.equals(periodo, students.periodo) && Objects.equals(cra, students.cra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula, program, campus, periodo, cra);
    }
}
