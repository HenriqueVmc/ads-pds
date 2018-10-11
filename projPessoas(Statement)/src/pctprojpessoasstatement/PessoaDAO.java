package pctprojpessoasstatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PessoaDAO {

    Connection conn = new Banco().getConexao();
    Statement stmt;

    public void salvar(Pessoa pessoa) throws SQLException {

        stmt = conn.createStatement();
        String insert = "INSERT INTO Pessoas (PrimeiroNome, UltimoNome)VALUES('" + pessoa.getPrimeiroNome() + "','" + pessoa.getUltimoNome() + "')";
        try {
            if (stmt.executeUpdate(insert) > 0) {
                System.out.println("Registro salvo!");
            } else {
                System.out.println("Erro ao salvar registro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remover(Pessoa pessoa) {
        try {

            stmt = conn.createStatement();

            String delete = "DELETE FROM Pessoas WHERE id = " + pessoa.getId();

            if (stmt.executeUpdate(delete) > 0) {
                System.out.println("Registro removido!");
            } else {
                System.out.println("Erro ao remover registro");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void atualizar(Pessoa pessoa) {
        try {

            stmt = conn.createStatement();

            String update = "UPDATE Pessoas SET primeiroNome = " + pessoa.getPrimeiroNome()
                    + ", ultimoNome = " + pessoa.getUltimoNome()
                    + ", idade = " + pessoa.getIdade()
                    + ", profissao = " + pessoa.getProfissao()
                    + " WHERE id = " + pessoa.getId();

            if (stmt.executeUpdate(update) > 0) {
                System.out.println("Registro alterado!");
            } else {
                System.out.println("Erro ao alterar registro");
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    public List<Pessoa> listar() {

        List<Pessoa> pessoas = null;
        try {

            stmt = conn.createStatement();

            String select = "SELECT * FROM pessoas";
            ResultSet rs = stmt.executeQuery(select);

            while (rs.next()) {

                Pessoa p = new Pessoa();

                p.setPrimeiroNome(rs.getString("primeiroNome"));
                p.setUltimoNome(rs.getString("ultimoNome"));
                p.setIdade(rs.getInt("idade"));
                p.setProfissao(rs.getString("profissao"));

                pessoas.add(p);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return pessoas;          
    }

    public Pessoa findById(int id) {

        Pessoa p = null;
        try {

            stmt = conn.createStatement();

            String select = "SELECT * FROM pessoas WHERE id = " + id;
            ResultSet rs = stmt.executeQuery(select);

            while (rs.next()) {

                p = new Pessoa();

                p.setPrimeiroNome(rs.getString("primeiroNome"));
                p.setUltimoNome(rs.getString("ultimoNome"));
                p.setIdade(rs.getInt("idade"));
                p.setProfissao(rs.getString("profissao"));

            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return p;
    }

}
