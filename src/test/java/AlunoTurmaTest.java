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

@DisplayName("Testes da Unidade AlunoTurma")
class AlunoTurmaTest {

    private Aluno aluno;
    private AlunoTurma alunoTurma;
    private String nomeTurma = "Física 1";

    @BeforeEach
    void setUp() {
        aluno = new Aluno("Isaac Newton", "1687", "Matemática", "newton", "123");
        alunoTurma = new AlunoTurma(aluno, nomeTurma);
    }

    @Test
    @DisplayName("Deve ser criado com estado inicial correto (0 faltas, 0 notas)")
    void deveSerCriadoComFaltasZeradasENotasVazias() {
        assertNotNull(alunoTurma.getAluno(), "O aluno não deveria ser nulo");
        assertEquals(0, alunoTurma.getFaltas(), "As faltas deveriam iniciar em 0");
        assertTrue(alunoTurma.getNotasPorUnidade().isEmpty(), "O mapa de notas deveria iniciar vazio");
    }

    // --- Grupo 1: Testes da Lógica de Notas ---
    @Nested
    @DisplayName("Lógica de Notas")
    class TestesDeNotas {

        @Test
        @DisplayName("Deve setar uma nota, sobrescrevê-la e obtê-la corretamente")
        void deveSetarSobrescreverEObterNota() {

            assertNull(alunoTurma.getNotaDaUnidade(1), "Nota da unidade 1 deveria ser nula inicialmente");


            alunoTurma.setNotaDaUnidade(1, 8.0);
            assertEquals(8.0, alunoTurma.getNotaDaUnidade(1), "Nota deveria ser 8.0");


            alunoTurma.setNotaDaUnidade(1, 10.0);
            assertEquals(10.0, alunoTurma.getNotaDaUnidade(1), "Nota deveria ter sido sobrescrita para 10.0");
        }
    }

    // --- Grupo 2: Testes da Lógica de Faltas ---
    @Nested
    @DisplayName("Lógica de Faltas")
    class TestesDeFaltas {

        @Test
        @DisplayName("Deve adicionar faltas sequencialmente")
        void deveAdicionarFaltaCorretamente() {
            assertEquals(0, alunoTurma.getFaltas());
            alunoTurma.adicionarFalta();
            assertEquals(1, alunoTurma.getFaltas());
            alunoTurma.adicionarFalta();
            assertEquals(2, alunoTurma.getFaltas());
        }

        @Test
        @DisplayName("Deve remover faltas quando o total for maior que zero")
        void deveRemoverFaltaCorretamente() {
            alunoTurma.setFaltas(5);
            alunoTurma.removerFalta();
            assertEquals(4, alunoTurma.getFaltas());
        }

        @Test
        @DisplayName("NÃO deve remover faltas se o total já for zero")
        void naoDeveRemoverFaltaSeJaEstiverEmZero() {
            assertEquals(0, alunoTurma.getFaltas());
            alunoTurma.removerFalta();
            assertEquals(0, alunoTurma.getFaltas());
        }
    }

    // --- Grupo 3: Testes de Segurança e Contrato Java ---
    @Nested
    @DisplayName("Segurança e Contrato (equals, hashCode, Encapsulamento)")
    class TestesDeContratoESeguranca {

        @Test
        @DisplayName("Deve retornar um mapa de notas não-modificável (segurança)")
        void deveRetornarMapaDeNotasNaoModificavel() {
            assertThrows(UnsupportedOperationException.class, () -> {

                alunoTurma.getNotasPorUnidade().put(1, 10.0);
            });
        }

        @Test
        @DisplayName("Deve considerar dois objetos iguais (equals) se o aluno e a turma são os mesmos")
        void deveConsiderarDoisAlunoTurmaIguaisSeAlunoETurmaSaoIguais() {
            AlunoTurma alunoTurma2 = new AlunoTurma(aluno, nomeTurma);
            assertEquals(alunoTurma, alunoTurma2, "Objetos deveriam ser iguais (equals)");
        }

        @Test
        @DisplayName("Deve gerar o mesmo hashCode se os objetos são iguais")
        void deveGerarMesmoHashCodeParaObjetosIguais() {
            AlunoTurma alunoTurma2 = new AlunoTurma(aluno, nomeTurma);
            assertEquals(alunoTurma.hashCode(), alunoTurma2.hashCode(), "HashCodes deveriam ser iguais");
        }

        @Test
        @DisplayName("Deve considerar objetos diferentes (equals) se os alunos são diferentes")
        void deveConsiderarDoisAlunoTurmaDiferentesSeAlunosSaoDiferentes() {
            Aluno aluno2 = new Aluno("Einstein", "1905", "Física", "ein", "123");
            AlunoTurma alunoTurma2 = new AlunoTurma(aluno2, nomeTurma);
            assertNotEquals(alunoTurma, alunoTurma2);
        }

        @Test
        @DisplayName("Deve considerar objetos diferentes (equals) se as turmas são diferentes")
        void deveConsiderarDoisAlunoTurmaDiferentesSeTurmasSaoDiferentes() {
            AlunoTurma alunoTurma2 = new AlunoTurma(aluno, "Física 2");
            assertNotEquals(alunoTurma, alunoTurma2);
        }
    }
}