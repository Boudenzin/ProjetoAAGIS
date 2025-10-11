package service;

import dao.AlunoDAO;
import dao.ProfessorDAO;
import model.Aluno;
import model.Professor;
import util.HashUtil;
import exceptions.UsuarioJaCadastradoException;

public class UsuarioService {
    private AlunoDAO alunoDAO;
    private ProfessorDAO professorDAO;

    public UsuarioService(AlunoDAO alunoDAO, ProfessorDAO professorDAO) {
        this.alunoDAO = alunoDAO;
        this.professorDAO = professorDAO;
    }

    // Cadastro de aluno
    public void cadastrarAluno(Aluno aluno) throws Exception {

        if (aluno == null) {
            throw new IllegalArgumentException("O objeto do aluno não pode ser nulo.");
        }

        if (alunoDAO.buscarPorUsuario(aluno.getUsuario()) != null) {
            throw new UsuarioJaCadastradoException("Usuário já existente.");
        }
        String senhaHash = HashUtil.hashSenha(aluno.getSenha());
        aluno.setSenha(senhaHash);
        alunoDAO.salvar(aluno);
    }

    // Cadastro de professor
    public void cadastrarProfessor(Professor professor) throws Exception {

        if (professor == null) {
            throw new IllegalArgumentException("O objeto do professor não pode ser nulo.");
        }

        if (professorDAO.buscarPorUsuario(professor.getUsuario()) != null) {
            throw new UsuarioJaCadastradoException("Usuário já existente.");
        }
        String senhaHash = HashUtil.hashSenha(professor.getSenha());
        professor.setSenha(senhaHash);
        professorDAO.salvar(professor);
    }

    // Login de aluno
    public Aluno autenticarAluno(String usuario, String senhaDigitada) {

        if (usuario == null || senhaDigitada == null) {
            return null;
        }

        Aluno aluno = alunoDAO.buscarPorUsuario(usuario);
        if (aluno != null && aluno.getSenha().equals(HashUtil.hashSenha(senhaDigitada))) {
            return aluno;
        }
        return null;
    }

    // Login de professor
    public Professor autenticarProfessor(String usuario, String senhaDigitada) {

        if (usuario == null || senhaDigitada == null) {
            return null;
        }

        Professor professor = professorDAO.buscarPorUsuario(usuario);
        if (professor != null && professor.getSenha().equals(HashUtil.hashSenha(senhaDigitada))) {
            return professor;
        }
        return null;
    }

    public Aluno buscarAlunoPorMatricula(String matricula) {
        return alunoDAO.buscarPorMatricula(matricula);
    }

}
