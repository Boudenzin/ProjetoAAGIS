import dao.TurmaDAO;
import exceptions.AlunoJaMatriculadoException;
import exceptions.TurmaJaCriadaException;
import exceptions.TurmaNaoEncontradaException;
import model.Aluno;
import model.Professor;
import model.Turma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.TurmaService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TurmaServiceTest {

    @Mock
    private TurmaDAO turmaDAO;

    @InjectMocks
    private TurmaService turmaService;

    @Nested
    class TestesCriarTurma {

        private Professor professorValido;
        private String nomeTurmaValido;

        @BeforeEach
        void setUp() {
            professorValido = new Professor("Marie Curie", "1903", "Física", "mcurie", "senha");
            nomeTurmaValido = "Física Quântica I";
        }

        @Test
        void deveCriarTurmaComSucessoQuandoDadosValidos() throws IOException, TurmaJaCriadaException {

            //Act
            turmaService.criarTurma(nomeTurmaValido, professorValido);

            //Assert
            ArgumentCaptor<Turma> turmaCaptor = ArgumentCaptor.forClass(Turma.class);

            verify(turmaDAO, times(1)).salvar(turmaCaptor.capture());

            Turma turmaSalva = turmaCaptor.getValue();

            assertNotNull(turmaSalva);
            assertEquals(nomeTurmaValido, turmaSalva.getNome());
            assertEquals(professorValido, turmaSalva.getProfessor());
        }

        @Test
        void deveLancarExcecaoAoCriarTurmaComProfessorNulo() {

            //Act & Assert
            assertThrows(IllegalArgumentException.class, () -> turmaService.criarTurma(nomeTurmaValido, null));

            verifyNoInteractions(turmaDAO);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void deveLancarExcecaoAoCriarTurmaComNomeInvalido(String nomeInvalido) {

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> turmaService.criarTurma(nomeInvalido, professorValido));

            verifyNoInteractions(turmaDAO);

        }

        @Test
        void deveLancarExcecaoAoCriarTurmaComNomeJaExistente() throws IOException {

            // Arrange
            // Simulamos que o DAO ENCONTRA uma turma quando procuramos pelo nome.
            // Isso significa que a turma já existe.
            when(turmaDAO.buscar(nomeTurmaValido)).thenReturn(new Turma(nomeTurmaValido, professorValido));

            // Act & Assert
            assertThrows(TurmaJaCriadaException.class, () -> { // <-- MUDANÇA AQUI
                turmaService.criarTurma(nomeTurmaValido, professorValido);
            });

            // Verificamos que o método salvar NUNCA foi chamado, pois a criação foi barrada.
            verify(turmaDAO, never()).salvar(any(Turma.class));
        }

    }

    @Nested
    class TestesAdicionarAluno {

        private Professor professorValido;
        private Aluno alunoValido;
        private Turma turmaValida;
        private String nomeTurmaValido;


        @BeforeEach
        void setUp() {
            professorValido = new Professor("Eric Farias", "1903", "Física", "ericfr", "senha");
            alunoValido = new Aluno("Isaac Newton", "1687", "Matemática", "newton", "senha");
            nomeTurmaValido = "Física Quântica I";

            turmaValida = new Turma(nomeTurmaValido, professorValido);
        }

        @Test
        void deveAdicionarAlunoComSucessoEmTurmaExistente() throws Exception {

            //Arrange
            when(turmaDAO.buscar(nomeTurmaValido)).thenReturn(turmaValida);

            //Act
            turmaService.adicionarAluno(nomeTurmaValido, alunoValido);

            //Assert
            verify(turmaDAO, times(1)).salvar(turmaValida);

            assertEquals(1, turmaValida.getParticipantes().size());
            assertEquals(alunoValido, turmaValida.getParticipantes().getFirst().getAluno());
        }

        @Test
        void deveLancarExcecaoAoAdicionarAlunoEmTurmaInexistente() throws IOException {

            //Arrange
            String nomeInexistente = "Turma Fantasma";
            when(turmaDAO.buscar(nomeInexistente)).thenReturn(null);

            //Act & Assert
            assertThrows(TurmaNaoEncontradaException.class, () -> turmaService.adicionarAluno(nomeInexistente, alunoValido));

            verify(turmaDAO, never()).salvar(any(Turma.class));
        }

        @Test
        void deveLancarExcecaoAoAdicionarAlunoNulo() throws IOException {

            //Act & Assert
            assertThrows(IllegalArgumentException.class, () -> turmaService.adicionarAluno(nomeTurmaValido, null));


            verify(turmaDAO, never()).salvar(any(Turma.class));
        }

        @Test
        void deveLancarExcecaoAoAdicionarAlunoJaeExistenteNaTurma() throws IOException, AlunoJaMatriculadoException {

            //Arrange
            turmaValida.adicionarAluno(alunoValido);

            when(turmaDAO.buscar(nomeTurmaValido)).thenReturn(turmaValida);

            //Act & Assert
            assertThrows(AlunoJaMatriculadoException.class, () -> turmaService.adicionarAluno(nomeTurmaValido, alunoValido));

            verify(turmaDAO, never()).salvar(any(Turma.class));
        }


    }

    @Nested
    class TestesRemoverAluno {

        private Professor professorValido;
        private Aluno alunoParaRemover;
        private Turma turmaValida;
        private String nomeTurmaValido;
        private String matriculaParaRemover;

        @BeforeEach
        void setUp() throws AlunoJaMatriculadoException {

            professorValido = new Professor("Eric Farias", "1903", "Física", "ericfr", "senha");
            alunoParaRemover = new Aluno("Isaac Newton", "1687", "Matemática", "newton", "senha");
            nomeTurmaValido = "Física Quântica I";
            matriculaParaRemover = "1687";

            turmaValida = new Turma(nomeTurmaValido, professorValido);

            turmaValida.adicionarAluno(alunoParaRemover);
        }

        @Test
        void deveRemoverAlunoComSucessoDeTurmaExistente() throws Exception {

            //Arrange
            when(turmaDAO.buscar(nomeTurmaValido)).thenReturn(turmaValida);

            assertEquals(1, turmaValida.getParticipantes().size());

            //Act
            turmaService.removerAluno(nomeTurmaValido, matriculaParaRemover);

            //Assert
            verify(turmaDAO, times(1)).salvar(turmaValida);
            assertEquals(0, turmaValida.getParticipantes().size());
        }

        @Test
        void naoDeveFalharAoRemoverAlunoInexistenteDaTurma() throws Exception {
            //Arrange
            String matriculaInexistente = "9999";

            when(turmaDAO.buscar(nomeTurmaValido)).thenReturn(turmaValida);

            //Act
            assertDoesNotThrow(() -> turmaService.removerAluno(nomeTurmaValido, matriculaInexistente));

            //Assert
            verify(turmaDAO, times(1)).salvar(turmaValida);
            // 2. O aluno original ("1687") AINDA deve estar na turma.
            assertEquals(1, turmaValida.getParticipantes().size());

        }

        @Test
        void deveLancarExcecaoAoRemoverAlunoDeTurmaInexistente() throws IOException {
            // Arrange
            String nomeTurmaInexistente = "Turma Fantasma";
            // Simulamos que o DAO não encontra a turma, retornando null.
            when(turmaDAO.buscar(nomeTurmaInexistente)).thenReturn(null);

            // Act & Assert
            // Esperamos que o serviço lance nossa exceção customizada.
            assertThrows(TurmaNaoEncontradaException.class, () -> {
                turmaService.removerAluno(nomeTurmaInexistente, matriculaParaRemover);
            });

            // Garantimos que o 'salvar' NUNCA foi chamado.
            verify(turmaDAO, never()).salvar(any(Turma.class));
        }

        @ParameterizedTest
        @NullAndEmptySource // Testa com null e string vazia ""
        void deveLancarExcecaoParaEntradasInvalidas(String inputInvalido) {

            // Act & Assert

            // Teste 1: Nome da turma inválido
            assertThrows(IllegalArgumentException.class, () -> {
                turmaService.removerAluno(inputInvalido, matriculaParaRemover);
            });

            // Teste 2: Matrícula do aluno inválida
            assertThrows(IllegalArgumentException.class, () -> {
                turmaService.removerAluno(nomeTurmaValido, inputInvalido);
            });

            // Garantimos que o DAO não foi chamado nenhuma vez
            verifyNoInteractions(turmaDAO);
        }




    }

}
