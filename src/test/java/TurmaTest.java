import exceptions.AlunoJaMatriculadoException;
import model.Aluno;
import model.AlunoTurma;
import model.Professor;
import model.Turma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TurmaTest {

    private String nomeTurma;
    private Turma turmaValida;
    private Professor professor;
    private Aluno alunoTeste;

    @BeforeEach
    void setUp() {

        professor = new Professor("Valdemir", "1903", "historia", "gargamel", "senha_valdemir");

        alunoTeste = new Aluno("Romildo", "2006", "lcc", "bidan", "senha_bidan");

        nomeTurma = "História da Paraíba";

        turmaValida = new Turma(nomeTurma, professor);


    }

    @Test
    @DisplayName("Deve ser criado com estado inicial correto")
    void deveIniciarComSetUpCorreto() {

        assertEquals(professor, turmaValida.getProfessor());
        assertEquals(nomeTurma, turmaValida.getNome());
        assertNotNull(turmaValida.getParticipantes());
        assertTrue(turmaValida.getParticipantes().isEmpty());
    }

}
