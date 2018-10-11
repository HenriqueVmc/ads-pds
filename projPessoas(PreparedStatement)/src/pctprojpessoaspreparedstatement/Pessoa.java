package pctprojpessoaspreparedstatement;

public class Pessoa {

    private int id, idade;
    private String ultimoNome, primeiroNome, profissao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public void imprimirPessoa() {
        System.out.println("Nome: " + getPrimeiroNome()
                + " Sobrenome: " + getUltimoNome()
                + " Idade: " + getIdade());
    }

    public void salvar() {

    }

}
