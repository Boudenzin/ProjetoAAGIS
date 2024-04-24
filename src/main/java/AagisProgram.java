import javax.swing.JOptionPane;
import java.io.IOException;
import java.util.List;

public class AagisProgram {
    public static void main(String[] args) {
        TurmaList sistemaTurmas = new TurmaList();
        String menu = """
            1. Cadastrar Turma
            2. Cadastrar Aluno Na Turma
            3. Ver Todos Os Alunos Da Turma
            4. Remover Aluno Da Turma
            5. Alterar Nota do Aluno da Turma
            6. Pesquisar Aluno Por Matrícula
            7. Sair

            """;

        boolean continuar = true;
        while (continuar){
            String opcao = JOptionPane.showInputDialog(null, menu);

            switch (opcao) {

                case "1":
                    try {
                        String nomeDaTurma = JOptionPane.showInputDialog(null, "Digite o nome da turma: ");
                        String nomeDocente = JOptionPane.showInputDialog(null, "Digite o nome do docente: ");
                        Turma novaTurma = new Turma(nomeDaTurma, nomeDocente);
                        sistemaTurmas.cadastrarNovaTurma(novaTurma);
                        JOptionPane.showMessageDialog(null, "Turma cadastrada com sucesso.");
                    } catch (TurmaJaCriadaException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        String nomeTurma = JOptionPane.showInputDialog(null, "Digite o nome da turma que deseja cadastrar o aluno(a)");
                        String nomeAluno = JOptionPane.showInputDialog(null, "Digite o nome do(a) aluno(a)");
                        String matricula = JOptionPane.showInputDialog(null, "Digite a matricula do aluno(a)");
                        String curso = JOptionPane.showInputDialog(null, "Digite o curso no qual o(a) aluno(a) está cursando");
                        Aluno novoAluno = new Aluno(nomeAluno, matricula, curso);
                        sistemaTurmas.cadastrarAlunoNaTurma(novoAluno, nomeTurma);
                        JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso.");
                    } catch (TurmaNaoEncontradaException | AlunoJaCadastradoException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;

                case "3":
                    try {
                        String turma = JOptionPane.showInputDialog(null, "Digite o nome da turma");
                        List<Aluno> alunosDaTurma = sistemaTurmas.listarAlunosDaTurma(turma);
                        if (alunosDaTurma.isEmpty()){
                            JOptionPane.showMessageDialog(null, "Não há alunos cadastrados na turma '"+ turma +"'");
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
                        String nomeTurma1 = JOptionPane.showInputDialog(null, "Digite o nome da turma");
                        String mAluno = JOptionPane.showInputDialog(null, "Digite o matricula do(a) aluno(a) que deseja remover");
                        sistemaTurmas.removerAlunoDaTurma(mAluno, nomeTurma1);
                    } catch (TurmaNaoEncontradaException | AlunoNaoEncontradoException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;

                case "5":
                    boolean inputValido = false;
                    double n1;
                    double n2;
                    double n3;
                    while (!inputValido){
                        try {
                            String nomTurma = JOptionPane.showInputDialog(null, "Digite o nome da turma");
                            String matrcl = JOptionPane.showInputDialog(null, "Digite a matricula do(a) aluno(a) que deseja alterar a nota");
                            Aluno a1 = sistemaTurmas.buscarAlunoPorMatricula(matrcl, nomTurma);

                            n1 = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite a primeira nota"));
                            n2 = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite a segunda nota"));
                            n3 = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite a terceira nota"));

                            sistemaTurmas.alterarNotasDoAluno(a1, n1, n2, n3);
                            JOptionPane.showMessageDialog(null, "Notas do aluno "+ a1.getNome() +" cadastradas com sucesso");
                            inputValido = true;
                        } catch (AlunoNaoEncontradoException | TurmaNaoEncontradaException e){
                            JOptionPane.showMessageDialog(null, e.getMessage());
                        } catch (NumberFormatException n) {
                            JOptionPane.showMessageDialog(null, "Entrada inválida, digite novamente.");
                        }
                    }
                    break;

                case "6":
                    try {
                        String nTurma = JOptionPane.showInputDialog(null, "Digite o nome da turma");
                        String mat = JOptionPane.showInputDialog(null, "Digite a matricula do aluno que deseja pesquisar");
                        Aluno alunoPesquisado = sistemaTurmas.buscarAlunoPorMatricula(mat, nTurma);
                        JOptionPane.showMessageDialog(null, alunoPesquisado.toString());
                    } catch (AlunoNaoEncontradoException | TurmaNaoEncontradaException e){
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                    break;

                case "7":
                    continuar = false;
                    JOptionPane.showMessageDialog(null, "Obrigado por usar o programa. Até mais!");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida. Tente novamente,,,,");
                    break;
            }
        }
    }
}