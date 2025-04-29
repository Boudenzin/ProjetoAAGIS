package components;

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
                    JOptionPane.showMessageDialog(null, "Dados de Turmas não puderam ser recuperados. Sistema iniciando sem nenhuma components.Turma.");
                }
            }

        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Dados de Turmas não puderam ser recuperados. Sistema iniciando sem nenhuma components.Turma.");
        }
        try {
            sistemaTurmas.recuperaAlunos();
        } catch (AlunoJaCadastradoException | TurmaNaoEncontradaException e) {
            JOptionPane.showMessageDialog(null, "Alguns dados de Alunos foram corrompidos.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Dados de Alunos não puderam ser recuperados. Sistema iniciando sem nenhum aluno");
        }

        String menu = """
            1. Cadastrar components.Turma
            2. Cadastrar components.Aluno Na components.Turma
            3. Ver Todos Os Alunos Da components.Turma
            4. Remover components.Aluno Da components.Turma
            5. Pesquisar components.Aluno Por Matrícula
            6. Remover components.Turma
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
                        Turma novaTurma = new Turma(nomeTurma, new Professor(nome));
                        sistemaTurmas.cadastrarNovaTurma(novaTurma);
                        JOptionPane.showMessageDialog(null, "components.Turma cadastrada com sucesso.");
                    } catch (TurmaJaCriadaException | NullPointerException e) {
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
                        JOptionPane.showMessageDialog(null, "components.Aluno cadastrado com sucesso.");
                    } catch (TurmaNaoEncontradaException | AlunoJaCadastradoException | NullPointerException e) {
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
                        matricula = JOptionPane.showInputDialog("Digite a matricula do(a) aluno(a) que deseja remover");
                        sistemaTurmas.removerAlunoDaTurma(matricula, nomeTurma);
                        JOptionPane.showMessageDialog(null, "components.Aluno removido com sucesso");
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
                        JOptionPane.showMessageDialog(null, "components.Turma removida com sucesso");
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
                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente");
                    break;
            }
        }
    }
}