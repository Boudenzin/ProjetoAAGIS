
import dao.AlunoDAO;
import dao.ProfessorDAO;
import model.Aluno;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.UsuarioService;
import model.Professor;
import org.junit.jupiter.api.BeforeEach;
import exceptions.UsuarioJaCadastradoException;
import org.junit.jupiter.api.Test;
import util.HashUtil;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private ProfessorDAO professorDAO;

    @Mock
    private AlunoDAO alunoDAO;

    @InjectMocks
    private UsuarioService usuarioService;

    @Nested
    class TestesDeProfessor {

        private static final String USUARIO_EINSTEIN = "einstein";
        private static final String SENHA_PLANA = "senha123";
        private static final String NOME_EINSTEIN = "Albert Einstein";
        private Professor professorValido;
        private String senhaHash;

        @BeforeEach
        void setUp() {
            professorValido = new Professor(NOME_EINSTEIN, "1905", "Física", USUARIO_EINSTEIN, SENHA_PLANA);
            senhaHash = HashUtil.hashSenha(SENHA_PLANA);
        }

        @Test
        void deveCadastrarProfessorComSucesso() throws Exception {
            //Arrange

            when(professorDAO.buscarPorUsuario(USUARIO_EINSTEIN)).thenReturn(null);

            //Act
            usuarioService.cadastrarProfessor(professorValido);

            //Asserts
            verify(professorDAO, times(1)).salvar(any(Professor.class));

        }

        @Test
        void deveLancarExcecaoAoTentarCadastrarProfessorComUsuarioExistente() throws Exception {
            //Arrange

            when(professorDAO.buscarPorUsuario(USUARIO_EINSTEIN)).thenReturn(professorValido);

            // Act e Assert
            assertThrows(UsuarioJaCadastradoException.class, () -> {
                usuarioService.cadastrarProfessor(professorValido);
            });

            verify(professorDAO, never()).salvar(any(Professor.class));
        }

        @Test
        void deveAutenticarProfessorComCredenciaisCorretas() {
            //Arrange

            professorValido.setSenha(senhaHash);
            when(professorDAO.buscarPorUsuario(USUARIO_EINSTEIN)).thenReturn(professorValido);

            //Act
            Professor resultado = usuarioService.autenticarProfessor(USUARIO_EINSTEIN, SENHA_PLANA);

            //Assert
            assertNotNull(resultado);
            assertEquals(NOME_EINSTEIN, resultado.getNome());

        }

        @Test
        void naoDeveAutenticarProfessorComSenhaIncorreta() {
            //Arrange
            professorValido.setSenha(senhaHash);
            when(professorDAO.buscarPorUsuario(USUARIO_EINSTEIN)).thenReturn(professorValido);

            //Act
            Professor resultado = usuarioService.autenticarProfessor(USUARIO_EINSTEIN, "senha_errada_123");

            //Assert
            assertNull(resultado, "A autenticação deveria falhar com senha incorreta, retornando null.");
        }

        @Test
        void deveLancarExcecaoAoTentarCadastrarProfessorNulo() {

            // Act & Assert: Verificamos se a exceção correta (IllegalArgumentException) é lançada
            assertThrows(IllegalArgumentException.class, () -> {
                usuarioService.cadastrarProfessor(null);
            });

            // Garantir que nenhuma interação com o DAO ocorreu
            verifyNoInteractions(professorDAO);
        }

        @Test
        void deveGarantirQueASenhaSalvaEhDiferenteDaSenhaPlana() throws Exception {

            //Arrange
            when(professorDAO.buscarPorUsuario(anyString())).thenReturn(null);
            ArgumentCaptor<Professor> professorCaptor = ArgumentCaptor.forClass(Professor.class);

            //Act
            usuarioService.cadastrarProfessor(professorValido);

            //Assert
            verify(professorDAO).salvar(professorCaptor.capture()); // Captura o professor

            Professor professorSalvo = professorCaptor.getValue();
            assertNotEquals("senha123", professorSalvo.getSenha()); // Garante que a senha não é a original
            assertNotNull(professorSalvo.getSenha());


        }


        @ParameterizedTest
        @CsvSource(value = {
                "einstein,       NULL",      // Usuário válido, senha nula
                "NULL,           senha123",  // Usuário nulo, senha válida
                "einstein,       ''",        // Usuário válido, senha vazia
                "'',             senha123",  // Usuário vazio, senha válida
                "NULL,           NULL",      // Usuário nulo E senha nula
                "'',             ''"        // Usuário vazio E senha vazia
        }, nullValues = {"NULL"})
        void deveRetornarNullParaCredenciaisInvalidasOuVaziasProfessor(String usuario, String senha) {
            // Act
            Professor resultado = usuarioService.autenticarProfessor(usuario, senha);

            // Assert
            assertNull(resultado, "Autenticação do professor deveria falhar para a combinação de entrada: [" + usuario + ", " + senha + "]");
        }

        @ParameterizedTest
        @ValueSource(strings = {"einstein", "Einstein", "EINSTEIN", "eInStEiN"}) // Várias combinações de case
        void deveAutenticarProfessorIgnorandoCaseDoUsuario(String usuarioDigitado) {
            // Arrange
            // O professor no "banco de dados" está salvo com o usuário todo em minúsculas.
            professorValido.setSenha(senhaHash);
            when(professorDAO.buscarPorUsuario(argThat(arg -> arg.equalsIgnoreCase(USUARIO_EINSTEIN))))
                    .thenReturn(professorValido);
            // Act
            // Tentamos autenticar com as diferentes combinações de case
            Professor resultado = usuarioService.autenticarProfessor(usuarioDigitado, SENHA_PLANA);

            // Assert
            // O resultado NUNCA deve ser nulo, o login deve funcionar para todos os casos.
            assertNotNull(resultado, "A autenticação deveria funcionar independentemente do case para o usuário: " + usuarioDigitado);
            assertEquals(NOME_EINSTEIN, resultado.getNome());
        }


    }

    @Nested
    class TestesDeAluno {
        private static final String USUARIO_NEWTON = "newton";
        private static final String SENHA_PLANA = "senha123";
        private static final String NOME_NEWTON = "Isaac Newton";

        private Aluno alunoValido;
        private String senhaHash;

        @BeforeEach
        void setUp() {
            alunoValido = new Aluno(NOME_NEWTON, "1687", "Matemática", USUARIO_NEWTON, SENHA_PLANA);
            senhaHash = HashUtil.hashSenha(SENHA_PLANA);
        }

        @Test
        void deveCadastrarAlunoComSucesso() throws Exception {
            //Arrange

            when(alunoDAO.buscarPorUsuario(USUARIO_NEWTON)).thenReturn(null);

            //Act

            usuarioService.cadastrarAluno(alunoValido);

            //Assert
            verify(alunoDAO, times(1)).salvar(any(Aluno.class));
        }

        @Test
        void deveLancarExcecaoAoTentarCadastrarAlunoComUsuarioExistente() throws Exception {

            //Arrange
            when(alunoDAO.buscarPorUsuario(USUARIO_NEWTON)).thenReturn(alunoValido);

            // Act e Assert
            assertThrows(UsuarioJaCadastradoException.class, () -> {
                usuarioService.cadastrarAluno(alunoValido);
            });

            verify(alunoDAO, never()).salvar(any(Aluno.class));
    }
        @Test
        void deveAutenticarAlunoComCredenciaisCorretas() {

            //Arrange
            alunoValido.setSenha(senhaHash);
            when(alunoDAO.buscarPorUsuario(USUARIO_NEWTON)).thenReturn(alunoValido);

            //Act
            Aluno resultado = usuarioService.autenticarAluno(USUARIO_NEWTON, SENHA_PLANA);

            //Assert
            assertNotNull(resultado);
            assertEquals(NOME_NEWTON, resultado.getNome());
        }

        @Test
        void naoDeveAutenticarAlunoComSenhaIncorreta() {

            //Arrange
            alunoValido.setSenha(senhaHash);
            when(alunoDAO.buscarPorUsuario(USUARIO_NEWTON)).thenReturn(alunoValido);

            //Act
            Aluno resultado = usuarioService.autenticarAluno(USUARIO_NEWTON, "senha_errada_123");

            //Assert
            assertNull(resultado, "A autenticação deveria falhar com senha incorreta, retornando null.");
        }

        @Test
        void deveLancarExcecaoAoTentarCadastrarAlunoNulo() {

            // Act & Assert: Verificamos se a exceção correta (IllegalArgumentException) é lançada
            assertThrows(IllegalArgumentException.class, () -> {
                usuarioService.cadastrarAluno(null);
            });

            // Garantir que nenhuma interação com o DAO ocorreu
            verifyNoInteractions(alunoDAO);
        }

        @Test
        void deveGarantirQueASenhaSalvaEhDiferenteDaSenhaPlana() throws Exception {

            //Arrange
            when(alunoDAO.buscarPorUsuario(anyString())).thenReturn(null);
            ArgumentCaptor<Aluno> alunoCaptor = ArgumentCaptor.forClass(Aluno.class);

            //Act
            usuarioService.cadastrarAluno(alunoValido);

            //Assert
            verify(alunoDAO).salvar(alunoCaptor.capture());

            Aluno alunoSalvo = alunoCaptor.getValue();
            assertNotEquals("senha123", alunoSalvo.getSenha());
            assertNotNull(alunoSalvo.getSenha());


        }

        @ParameterizedTest
        @CsvSource(value = {
                "newton,         NULL",      // Usuário válido, senha nula
                "NULL,           senha123",  // Usuário nulo, senha válida
                "newton,         ''",        // Usuário válido, senha vazia
                "'',             senha123",  // Usuário vazio, senha válida
                "NULL,           NULL",      // Usuário nulo E senha nula
                "'',             ''"        // Usuário vazio E senha vazia

        }, nullValues =  {"NULL"})
        void deveRetornarNullParaCredenciaisInvalidasOuVazias(String usuario, String senha) {
            // Act
            Aluno resultado = usuarioService.autenticarAluno(usuario, senha);

            // Assert
            assertNull(resultado, "Autenticação deveria falhar para a combinação de entrada: [" + usuario + ", " + senha + "]");
        }

        @ParameterizedTest
        @ValueSource(strings = {"newton", "Newton", "NEWTON", "nEwToN"}) // Várias combinações de case
        void deveAutenticarAlunoIgnorandoCaseDoUsuario(String usuarioDigitado) {
            // Arrange
            // O aluno no "banco de dados" está salvo com o usuário todo em minúsculas.
            alunoValido.setSenha(senhaHash);
            when(alunoDAO.buscarPorUsuario(argThat(arg -> arg.equalsIgnoreCase(USUARIO_NEWTON))))
                    .thenReturn(alunoValido);
            // Act
            // Tentamos autenticar com as diferentes combinações de case
            Aluno resultado = usuarioService.autenticarAluno(usuarioDigitado, SENHA_PLANA);

            // Assert
            // O resultado NUNCA deve ser nulo, o login deve funcionar para todos os casos.
            assertNotNull(resultado, "A autenticação deveria funcionar independentemente do case para o usuário: " + usuarioDigitado);
            assertEquals(NOME_NEWTON, resultado.getNome());
        }

        @Test
        void deveRetornarAlunoQuandoMatriculaExistir() {
            //Arrange
            String matriculaExistente = "1687";
            when(alunoDAO.buscarPorMatricula(matriculaExistente)).thenReturn(alunoValido);

            //Act
            Aluno resultado = usuarioService.buscarAlunoPorMatricula(matriculaExistente);

            //Assert
            assertNotNull(resultado);
            assertEquals(matriculaExistente, resultado.getMatricula());
            assertEquals(NOME_NEWTON, resultado.getNome());
        }

        @Test
        void deveRetornarNullQuandoMatriculaNaoExistir() {
            // Arrange
            String matriculaInexistente = "matricula_que_nao_existe";
            when(alunoDAO.buscarPorMatricula(matriculaInexistente)).thenReturn(null);

            //Act
            Aluno resultado = usuarioService.buscarAlunoPorMatricula(matriculaInexistente);

            //Assert
            assertNull(resultado);
        }

        @ParameterizedTest
        @NullAndEmptySource // Fornece um valor nulo e uma string vazia ("") para o teste
        @ValueSource(strings = {"  ", "\t", "\n"}) // Fornece strings que contêm apenas espaços em branco
        void deveRetornarNullSeMatriculaForInvalida(String matriculaInvalida) {

            // Act
            Aluno resultado = usuarioService.buscarAlunoPorMatricula(matriculaInvalida);

            // Assert
            assertNull(resultado);
            verifyNoInteractions(alunoDAO);
        }




    }




}
