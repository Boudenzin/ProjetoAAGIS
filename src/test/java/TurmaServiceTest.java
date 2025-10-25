import dao.TurmaDAO;
import exceptions.*;
import model.Aluno;
import model.AlunoTurma;
import model.Professor;
import model.Turma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
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
            assertThrows(TurmaJaCriadaException.class, () -> turmaService.criarTurma(nomeTurmaValido, professorValido));

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
            turmaService.adicionarAluno(nomeTurmaValido, alunoValido, professorValido);

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
            assertThrows(TurmaNaoEncontradaException.class, () -> turmaService.adicionarAluno(nomeInexistente, alunoValido, professorValido));

            verify(turmaDAO, never()).salvar(any(Turma.class));
        }

        @Test
        void deveLancarExcecaoAoAdicionarAlunoNulo() throws IOException {

            //Act & Assert
            assertThrows(IllegalArgumentException.class, () -> turmaService.adicionarAluno(nomeTurmaValido, null, professorValido));


            verify(turmaDAO, never()).salvar(any(Turma.class));
        }

        @Test
        void deveLancarExcecaoAoAdicionarAlunoJaeExistenteNaTurma() throws IOException, AlunoJaMatriculadoException {

            //Arrange
            turmaValida.adicionarAluno(alunoValido);

            when(turmaDAO.buscar(nomeTurmaValido)).thenReturn(turmaValida);

            //Act & Assert
            assertThrows(AlunoJaMatriculadoException.class, () -> turmaService.adicionarAluno(nomeTurmaValido, alunoValido, professorValido));

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
            turmaService.removerAluno(nomeTurmaValido, matriculaParaRemover, professorValido);

            //Assert
            verify(turmaDAO, times(1)).salvar(turmaValida);
            assertEquals(0, turmaValida.getParticipantes().size());
        }

        @Test
        void deveLancarExcecaoAoRemoverAlunoInexistenteDaTurma() throws Exception {
            //Arrange
            String matriculaInexistente = "9999";

            when(turmaDAO.buscar(nomeTurmaValido)).thenReturn(turmaValida);

            //Act
            assertThrows(AlunoNaoEncontradoException.class, () -> turmaService.removerAluno(nomeTurmaValido, matriculaInexistente, professorValido));
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
            // Esperamos que o serviço lance uma exceção customizada.
            assertThrows(TurmaNaoEncontradaException.class, () -> turmaService.removerAluno(nomeTurmaInexistente, matriculaParaRemover, professorValido));

            // Garantimos que o 'salvar' NUNCA foi chamado.
            verify(turmaDAO, never()).salvar(any(Turma.class));
        }

        @ParameterizedTest
        @NullAndEmptySource // Testa com null e string vazia ""
        void deveLancarExcecaoParaEntradasInvalidas(String inputInvalido) {

            // Act & Assert

            // Teste 1: Nome da turma inválido
            assertThrows(IllegalArgumentException.class, () -> turmaService.removerAluno(inputInvalido, matriculaParaRemover, professorValido));

            // Teste 2: Matrícula do aluno inválida
            assertThrows(IllegalArgumentException.class, () -> turmaService.removerAluno(nomeTurmaValido, inputInvalido, professorValido));

            // Garantimos que o DAO não foi chamado nenhuma vez
            verifyNoInteractions(turmaDAO);
        }




    }

    @Nested
    class TestesAtualizarNota {

        // --- Atores e Cenário ---
        private Professor professorValido;
        private Aluno alunoValido;
        private Turma turmaValida;
        private AlunoTurma alunoTurmaValido;

        // --- Dados Válidos para o Teste ---
        private String nomeTurmaValido;
        private String matriculaValida;
        private int unidadeValida;
        private double notaValida;

        @BeforeEach
        void setUp() throws AlunoJaMatriculadoException {

            // 1. Criar os atores principais
            professorValido = new Professor("Eric Farias", "1903", "Física", "ericfr", "senha");
            alunoValido = new Aluno("Isaac Newton", "1687", "Matemática", "newton", "senha");

            // 2. Criar a turma (o "cenário")
            nomeTurmaValido = "Física Quântica I";
            turmaValida = new Turma(nomeTurmaValido, professorValido);

            // 3. Colocar o aluno dentro da turma
            // (Usamos a própria lógica do modelo que já refatoramos,
            // garantindo que o AlunoTurma seja criado corretamente)
            turmaValida.adicionarAluno(alunoValido);

            // 4. Pegar a referência do "participante" (AlunoTurma) que foi criado
            matriculaValida = "1687";
            alunoTurmaValido = turmaValida.getParticipantePorMatricula(matriculaValida)
                    .orElseThrow();

            // 5. Definir os dados válidos de entrada para o "caminho feliz"
            unidadeValida = 1;
            notaValida = 9.5;
        }

        @Test
        void deveAtualizarNotaComSucessoQuandoTodosOsDadosSaoValidos() throws Exception {

            //Arrange
            when(turmaDAO.buscar(nomeTurmaValido)).thenReturn(turmaValida);

            assertNull(alunoTurmaValido.getNotaDaUnidade(unidadeValida), "A nota deveria ser nula antes de ser lançada.");
            //Act
            turmaService.atualizarNota(nomeTurmaValido, matriculaValida, unidadeValida, notaValida, professorValido);

            //Assert
            verify(turmaDAO, times(1)).salvar(turmaValida);

            assertEquals(notaValida, alunoTurmaValido.getNotaDaUnidade(unidadeValida));

        }

        @ParameterizedTest
        @ValueSource(ints = {0, 5, -1, 100})
            // Lista de unidades inválidas (< 1 ou > 4)
        void deveLancarUnidadeInvalidaExceptionParaValoresForaDoIntervalo(int unidadeInvalida) {
            // Act & Assert
            assertThrows(UnidadeInvalidaException.class, () -> {
                turmaService.atualizarNota(nomeTurmaValido, matriculaValida, unidadeInvalida, notaValida, professorValido);
            });

            verifyNoInteractions(turmaDAO);
        }

        @ParameterizedTest
        @ValueSource(doubles = {-0.1, 10.1, -100.0, 11.0}) // Lista de notas inválidas (< 0 ou > 10)
        void deveLancarNotaInvalidaExceptionParaValoresForaDoIntervalo(double notaInvalida) {

            // Act & Assert
            assertThrows(NotaInvalidaException.class, () -> {
                turmaService.atualizarNota(nomeTurmaValido, matriculaValida, unidadeValida, notaInvalida, professorValido);
            });

            verifyNoInteractions(turmaDAO);

        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "\t"})
        void deveLancarIllegalArgumentExceptionSeMatriculaForNulaOuVazia(String matriculaInvalida) {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> {
                turmaService.atualizarNota(nomeTurmaValido, matriculaInvalida, unidadeValida, notaValida, professorValido);
            });

            // Assert (Extra)
            verifyNoInteractions(turmaDAO);
        }

        @Test
        void deveLancarAlunoNaoEncontradoExceptionSeAlunoNaoEstiverNaTurma() throws Exception {
            // Arrange
            String matriculaInexistente = "99999";

            when(turmaDAO.buscar(nomeTurmaValido)).thenReturn(turmaValida);

            // Act & Assert
            Exception exception = assertThrows(AlunoNaoEncontradoException.class, () -> {
                turmaService.atualizarNota(nomeTurmaValido, matriculaInexistente, unidadeValida, notaValida, professorValido);
            });

             assertTrue(exception.getMessage().contains(matriculaInexistente));

            verify(turmaDAO, never()).salvar(any(Turma.class));
        }

        @Test
        void deveLancarPersistenciaExceptionSeHouverErroAoSalvar() throws Exception {
            // Arrange
            when(turmaDAO.buscar(nomeTurmaValido)).thenReturn(turmaValida);

            doThrow(new IOException("Simulação de disco cheio"))
                    .when(turmaDAO).salvar(any(Turma.class));

            // Act & Assert
            PersistenciaException exception = assertThrows(PersistenciaException.class, () -> {
                // Usamos todos os dados válidos, pois a falha deve ocorrer no ÚLTIMO passo
                turmaService.atualizarNota(nomeTurmaValido, matriculaValida, unidadeValida, notaValida, professorValido);
            });

            // 4. Verificamos se a exceção tem a causa original (o IOException) anexada
            assertNotNull(exception.getCause());
            assertInstanceOf(IOException.class, exception.getCause());
        }
    }
}
