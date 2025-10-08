
import dao.AlunoDAO;
import dao.ProfessorDAO;
import model.Aluno;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.UsuarioService;
import model.Professor;
import org.junit.jupiter.api.BeforeEach;
import exceptions.UsuarioJaCadastradoException;
import org.junit.jupiter.api.Test;
import util.HashUtil;

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
        private Professor professorValido;
        private String senhaPlana = "senha123";
        private String senhaHash;

        @BeforeEach
        void setUp() {
            professorValido = new Professor("Albert Einstein", "1905", "Física", "einstein", senhaPlana);
            senhaHash = HashUtil.hashSenha(senhaPlana);
        }

        @Test
        void deveCadastrarProfessorComSucesso() throws Exception {
            //Arrange

            when(professorDAO.buscarPorUsuario("einstein")).thenReturn(null);

            //Act
            usuarioService.cadastrarProfessor(professorValido);

            //Asserts
            verify(professorDAO, times(1)).salvar(any(Professor.class));

        }

        @Test
        void deveLancarExcecaoAoTentarCadastrarProfessorComUsuarioExistente() throws Exception {
            //Arrange

            when(professorDAO.buscarPorUsuario("einstein")).thenReturn(professorValido);

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
            when(professorDAO.buscarPorUsuario("einstein")).thenReturn(professorValido);

            //Act
            Professor resultado = usuarioService.autenticarProfessor("einstein", senhaPlana);

            //Assert
            assertNotNull(resultado);
            assertEquals("Albert Einstein", resultado.getNome());

        }

        @Test
        void naoDeveAutenticarProfessorComSenhaIncorreta() {
            //Arrange
            professorValido.setSenha(senhaHash);
            when(professorDAO.buscarPorUsuario("einstein")).thenReturn(professorValido);

            //Act
            Professor resultado = usuarioService.autenticarProfessor("einstein", "senha_errada_123");

            //Assert
            assertNull(resultado, "A autenticação deveria falhar com senha incorreta, retornando null.");
        }

        @Test
        void naoDeveAutenticarProfessorComUsuarioInexistente() {
            //Arrange
            when(professorDAO.buscarPorUsuario("usuario_inexistente")).thenReturn(null);

            //Act
            Professor resultado = usuarioService.autenticarProfessor("usuario_inexistente", "qualquer_senha");

            //Assert
            assertNull(resultado, "A autenticação deveria falhar com usuário inexistente.");
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
    }

    @Nested
    class TestesDeAluno {
        private Aluno alunoValido;
        private String senhaPlana = "senha123";
        private String senhaHash;

        @BeforeEach
        void setUp() {
            alunoValido = new Aluno("Isaac Newton", "1687", "Matemática", "newton", senhaPlana);
            senhaHash = HashUtil.hashSenha(senhaPlana);
        }

        @Test
        void deveCadastrarAlunoComSucesso() throws Exception {
            //Arrange

            when(alunoDAO.buscarPorUsuario("newton")).thenReturn(null);

            //Act

            usuarioService.cadastrarAluno(alunoValido);

            //Assert
            verify(alunoDAO, times(1)).salvar(any(Aluno.class));
        }

        @Test
        void deveLancarExcecaoAoTentarCadastrarAlunoComUsuarioExistente() throws Exception {

            //Arrange
            when(alunoDAO.buscarPorUsuario("newton")).thenReturn(alunoValido);

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
            when(alunoDAO.buscarPorUsuario("newton")).thenReturn(alunoValido);

            //Act
            Aluno resultado = usuarioService.autenticarAluno("newton", senhaPlana);

            //Assert
            assertNotNull(resultado);
            assertEquals("Isaac Newton", resultado.getNome());
        }

        @Test
        void naoDeveAutenticarAlunoComSenhaIncorreta() {

            //Arrange
            alunoValido.setSenha(senhaHash);
            when(alunoDAO.buscarPorUsuario("newton")).thenReturn(alunoValido);

            //Act
            Aluno resultado = usuarioService.autenticarAluno("newton", "senha_errada_123");

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

    }




}
