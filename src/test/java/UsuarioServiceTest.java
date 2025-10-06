
import dao.AlunoDAO;
import dao.ProfessorDAO;
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

    //deveAutenticarProfessorComCredenciaisCorretas()


    //naoDeveAutenticarProfessorComSenhaIncorreta()

    //naoDeveAutenticarProfessorComUsuarioInexistente()
}
