package model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AlunoTurma implements Serializable {

    private Aluno aluno;
    private String nomeTurma;
    private Map<Integer, Double> notasPorUnidade; // chave = número da unidade, valor = nota
    private int faltas;

    public AlunoTurma(Aluno aluno, String nomeTurma) {
        this.aluno = aluno;
        this.nomeTurma = nomeTurma;
        this.notasPorUnidade = new HashMap<>();
        this.faltas = 0;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Map<Integer, Double> getNotasPorUnidade() {
        return Collections.unmodifiableMap(notasPorUnidade);
    }

    public void setNotaDaUnidade(int unidade, double nota) {
        notasPorUnidade.put(unidade, nota);
    }

    public Double getNotaDaUnidade(int unidade) {
        return notasPorUnidade.get(unidade); // pode retornar null se ainda não tiver nota
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public void adicionarFalta() {
        this.faltas++;
    }

    public void removerFalta() {
        if (this.faltas > 0) {
            this.faltas--;
        }
    }

    public String getNomeTurma() {
        return nomeTurma;
    }

    public void setNomeTurma(String nomeTurma) {
        this.nomeTurma = nomeTurma;
    }

    @Override
    public String toString() {
        return aluno.getNome();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlunoTurma that = (AlunoTurma) o;
        // A unicidade é definida pelo Aluno E pelo nome da Turma
        return Objects.equals(aluno, that.aluno) &&
                Objects.equals(nomeTurma, that.nomeTurma);
    }

    @Override
    public int hashCode() {
        // Usa os mesmos campos do equals() para gerar o hash
        return Objects.hash(aluno, nomeTurma);
    }
}
