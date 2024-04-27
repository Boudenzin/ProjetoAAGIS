import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.List;

public class AagisProgram {
    public static void main(String[] args) {
        TurmaList sistemaTurmas = new TurmaList();
        try {
            List<Turma> turmas = sistemaTurmas.recuperaTurma();
            for (Turma t : turmas) {
                try {
                    sistemaTurmas.cadastrarNovaTurma(t);
                } catch (TurmaJaCriadaException e) {
                    JOptionPane.showMessageDialog(null, "Dados de Turmas não puderam ser recuperados. Sistema iniciando sem nenhuma Turma.");
                }
            }

        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Dados de Turmas não puderam ser recuperados. Sistema iniciando sem nenhuma Turma.");
        }
        try {
            sistemaTurmas.recuperaAlunos();
        } catch (AlunoJaCadastradoException | TurmaNaoEncontradaException e) {
            JOptionPane.showMessageDialog(null, "Alguns dados de Alunos foram corrompidos.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Dados de Alunos não puderam ser recuperados. Sistema iniciando sem nenhum aluno");
        }

        String menu = """
            1. Cadastrar Turma
            2. Cadastrar Aluno Na Turma
            3. Ver Todos Os Alunos Da Turma
            4. Remover Aluno Da Turma
            5. Pesquisar Aluno Por Matrícula
            6. Remover Turma
            7. Sair

            """;

        boolean continuar = true;
        while (continuar){
            String nomeTurma;
            String nomeDocente;
            String nomeAluno;
            String matricula;
            String curso;

            String opcao = JOptionPane.showInputDialog(null, menu);
            switch (opcao) {

                case "1":
                    try {
                        nomeTurma = JOptionPane.showInputDialog("Digite o nome da turma: ");
                        nomeDocente = JOptionPane.showInputDialog("Digite o nome do docente: ");
                        Turma novaTurma = new Turma(nomeTurma, nomeDocente);
                        sistemaTurmas.cadastrarNovaTurma(novaTurma);
                        JOptionPane.showMessageDialog(null, "Turma cadastrada com sucesso.");
                    } catch (TurmaJaCriadaException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        nomeTurma = JOptionPane.showInputDialog("Digite o nome da turma que deseja cadastrar o(a) aluno(a)");
                        nomeAluno = JOptionPane.showInputDialog("Digite o nome do(a) aluno(a)");
                        matricula = JOptionPane.showInputDialog("Digite a matricula do(a) aluno(a)");
                        curso = JOptionPane.showInputDialog("Digite o curso que o(a) aluno(a) está cursando");
                        Aluno novoAluno = new Aluno(nomeAluno, matricula, curso);
                        sistemaTurmas.cadastrarAlunoNaTurma(novoAluno, nomeTurma);
                        JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso.");
                    } catch (TurmaNaoEncontradaException | AlunoJaCadastradoException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;

                case "3":
                    try {
                        nomeTurma = JOptionPane.showInputDialog("Digite o nome da turma");
                        List<Aluno> alunosDaTurma = sistemaTurmas.listarAlunosDaTurma(nomeTurma);
                        if (alunosDaTurma.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Não há alunos cadastrados na turma '"+ nomeTurma +"'");
                        } else {
                            StringBuilder saida = new StringBuilder();
                            for (Aluno a: alunosDaTurma){
                                saida.append(a.toString()).append("\n");
                            }
                            JOptionPane.showMessageDialog(null, saida.toString());
                        }
                    } catch (TurmaNaoEncontradaException e){
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;

                case "4":
                    try {
                        nomeTurma = JOptionPane.showInputDialog("Digite o nome da turma");
                        matricula = JOptionPane.showInputDialog("Digite o matricula do(a) aluno(a) que deseja remover");
                        sistemaTurmas.removerAlunoDaTurma(matricula, nomeTurma);
                        JOptionPane.showMessageDialog(null, "Aluno removido com sucesso");
                    } catch (TurmaNaoEncontradaException | AlunoNaoEncontradoException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;

                case "5":
                    try {
                        nomeTurma = JOptionPane.showInputDialog("Digite o nome da turma");
                        matricula = JOptionPane.showInputDialog("Digite a matricula do(a) aluno(a) que deseja pesquisar");
                        Aluno alunoPesquisado = sistemaTurmas.buscarAlunoPorMatricula(matricula, nomeTurma);
                        JOptionPane.showMessageDialog(null, alunoPesquisado.toString());
                    } catch (AlunoNaoEncontradoException | TurmaNaoEncontradaException e){
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;

                case "6":
                    try {
                        nomeTurma = JOptionPane.showInputDialog("Digite o nome da turma que deseja remover");
                        sistemaTurmas.removerTurma(nomeTurma);
                        JOptionPane.showMessageDialog(null, "Turma removida com sucesso");
                    } catch (TurmaNaoEncontradaException e){
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }

                    break;

                case "7":
                    continuar = false;
                    try {
                        sistemaTurmas.gravaTurmas();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Não foi possível salvar os dados de Turmas.");
                    }
                    try {
                        sistemaTurmas.gravaAlunos();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Não foi possível salvar os dados de Turmas.");
                    }
                    JOptionPane.showMessageDialog(null, "Obrigado por usar o programa. Até mais!");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente,,,,");
                    break;
            }
        }
    }
}