import dao.TurmaDAO;
import exceptions.TurmaJaCriadaException;
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
            assertThrows(IllegalArgumentException.class, () -> {
                turmaService.criarTurma(nomeTurmaValido, null);
            });

            verifyNoInteractions(turmaDAO);
        }

        @ParameterizedTest
        @NullAndEmptySource
        void deveLancarExcecaoAoCriarTurmaComNomeInvalido(String nomeInvalido) {

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> {
                turmaService.criarTurma(nomeInvalido, professorValido);
            });

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
}
