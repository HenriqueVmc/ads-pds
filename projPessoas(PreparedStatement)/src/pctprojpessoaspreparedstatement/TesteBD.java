package pctprojpessoaspreparedstatement;

import java.sql.SQLException;
import java.util.List;

public class TesteBD {

    public static void main(String[] args) throws SQLException {

        Pessoa p1 = new Pessoa();
        p1.setPrimeiroNome("Teste");
        p1.setUltimoNome("Silva");

        Pessoa p2 = new Pessoa();
        p2.setPrimeiroNome("Teste2");
        p2.setUltimoNome("Silva2");

        PessoaDAO pDAO = new PessoaDAO();

        pDAO.salvar(p1);
        pDAO.salvar(p2);

        p2.setId(40);
        pDAO.atualizar(p2);
        
        Pessoa find = pDAO.findById(p1.getId());
        find.imprimirPessoa();

        pDAO.remover(p1);

        for (Pessoa p : (List<Pessoa>) pDAO.listar()) {
            p.imprimirPessoa();
        }

    }
}
