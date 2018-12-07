package pct;

/**
 * @author Alunos
 */
public class Aluno {

    private String nome, cpf, sexo;
    private double renda;
    private short ano;
    private boolean privado;

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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
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

    public boolean isPrivado() {
        return privado;
    }

    public void setPrivado(boolean privado) {
        this.privado = privado;
    }

     

}
