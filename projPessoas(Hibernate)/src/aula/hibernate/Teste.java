package aula.hibernate;

public class Teste {

    public static void main(String[] args) {
        Pessoas pessoa = new Pessoas();
        pessoa.setIdade(85);
        pessoa.setPrimeiroNome("JO");
        pessoa.setProfissao("Jornalista");
        pessoa.setUltimoNome("Soares");

        Pessoas pessoa2 = new Pessoas();
        pessoa2.setIdade(60);
        pessoa2.setPrimeiroNome("Jair");
        pessoa2.setProfissao("Mito");
        pessoa2.setUltimoNome("Bolsonaro");

        PessoasDAO pDAO = new PessoasDAO();
        pDAO.salvar(pessoa);
        pDAO.salvar(pessoa2);
        
        Pessoas find = pDAO.findById(pessoa.getId());
        System.out.println("Nome: "+find.getPrimeiroNome());
        
        pessoa.setIdade(90);
        pDAO.atualizar(pessoa);    
        
        pDAO.listar();
        pDAO.remover(pessoa2);
        pDAO.listar();
    }
}
