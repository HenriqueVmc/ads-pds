package pct;

/**
 * @author Alunos
 */
public class Aluno {

    private String nome, cpf, curso;
    private double renda;
    private short ano, sexo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public short getSexo() {
        return sexo;
    }

    public void setSexo(short sexo) {
        this.sexo = sexo;
    }

    public double getRendaFamiliar() {
        return renda;
    }

    public void setRendaFamiliar(double renda) {
        this.renda = renda;
    }

    public short getAno() {
        return ano;
    }

    public void setAno(short ano) {
        this.ano = ano;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

     

}
