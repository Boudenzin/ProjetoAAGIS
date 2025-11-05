import exceptions.AlunoJaMatriculadoException;
import exceptions.AlunoNaoEncontradoException;
import model.Aluno;
import model.AlunoTurma;
import model.Professor;
import model.Turma;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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

    @Test
    @DisplayName("Deve adicionar um aluno com sucesso")
    void deveAdicionarAlunoComSucesso() throws AlunoJaMatriculadoException {

        turmaValida.adicionarAluno(alunoTeste);

        assertEquals(1, turmaValida.getParticipantes().size(), "O tamanho da lista deveria ser 1");
        assertEquals(alunoTeste, turmaValida.getParticipantes().getFirst().getAluno());


    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar adicionar o mesmo aluno duas vezes")
    void deveLancarExcecaoAoAdicionarAlunoDuplicado() throws AlunoJaMatriculadoException {

        turmaValida.adicionarAluno(alunoTeste);

        assertThrows(AlunoJaMatriculadoException.class, () -> turmaValida.adicionarAluno(alunoTeste));

        assertEquals(1, turmaValida.getParticipantes().size());
    }

    @Test
    @DisplayName("Deve retornar uma lista de participantes que não pode ser modificada")
    void deveRetornarListaDeParticipantesNaoModificavel() {
        //Arrange
        List<AlunoTurma> listaDeFora = turmaValida.getParticipantes();
        Aluno outroAluno = new Aluno("Outro", "999", "Curso", "outro", "123");

        assertThrows(UnsupportedOperationException.class, () -> listaDeFora.add(new AlunoTurma(outroAluno, nomeTurma)));
    }

    @Test
    @DisplayName("Deve remover o aluno com sucesso a partir da matrícula")
    void deveRemoverAlunoComSucesso() throws AlunoJaMatriculadoException, AlunoNaoEncontradoException {

        //Arrange
        turmaValida.adicionarAluno(alunoTeste);

        //Act
        turmaValida.removerAlunoPorMatricula(alunoTeste.getMatricula());

        //Assert
        assertEquals(0, turmaValida.getParticipantes().size());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao remover um aluno que não existe")
    void deveLancarExcecaoAoRemoverAlunoInexistente() {

        //Act & Assert
        assertThrows(AlunoNaoEncontradoException.class, () -> turmaValida.removerAlunoPorMatricula("999"));
    }

    @Test
    @DisplayName("Deve encontrar o aluno quando o aluno está na turma")
    void deveEncontrarParticipanteQuandoEleExiste() throws AlunoJaMatriculadoException {

        //Arrange
        turmaValida.adicionarAluno(alunoTeste);

        //Act
        Optional<AlunoTurma> resultadoOptional = turmaValida.getParticipantePorMatricula(alunoTeste.getMatricula());


        //Assert
        assertTrue(resultadoOptional.isPresent(), "O aluno deveria ter sido encontrado.");

        AlunoTurma participanteEncontrado = resultadoOptional.get();

        assertEquals(alunoTeste, participanteEncontrado.getAluno());
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando o aluno não está na turma")
    void deveRetornarOptionalVazioQuandoAlunoNaoExiste() {

        //Act
        Optional<AlunoTurma> resultadoOptional = turmaValida.getParticipantePorMatricula("999");

        //Assert
        assertTrue(resultadoOptional.isEmpty(), "O Optional deveria estar vazio, pois o aluno não existe.");
    }

    @Test
    @DisplayName("Deve atualizar a nota com sucesso")
    void deveAtualizarNotaNormalmente() throws AlunoJaMatriculadoException, AlunoNaoEncontradoException {

        //Arrange
        turmaValida.adicionarAluno(alunoTeste);
        int unidade1 = 1;
        double nota1 = 9.3;


        //Act
        turmaValida.atualizarNotaDoAluno(alunoTeste.getMatricula(), unidade1, nota1);

        //Assert
        Optional<AlunoTurma> atOptional = turmaValida.getParticipantePorMatricula(alunoTeste.getMatricula());
        double notaSalva = atOptional.get().getNotaDaUnidade(unidade1);

        assertEquals(nota1, notaSalva);
    }

}
