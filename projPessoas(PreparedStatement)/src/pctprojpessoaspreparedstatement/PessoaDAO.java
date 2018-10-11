package pctprojpessoaspreparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PessoaDAO {
    
    Connection conn = new Banco().getConexao();
    PreparedStatement ps;
    
    public void salvar(Pessoa pessoa) throws SQLException {
        String insert = "INSERT INTO Pessoas (PrimeiroNome, UltimoNome)VALUES(?, ?)";
        ps = conn.prepareStatement(insert);
        ps.setString(1, pessoa.getPrimeiroNome());
        ps.setString(2, pessoa.getUltimoNome());
        
        try {
            if (ps.execute()) {
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
            String delete = "DELETE FROM Pessoas WHERE id = ?";
            ps = conn.prepareStatement(delete);
            ps.setInt(1, pessoa.getId());
            
            if (ps.execute()) {
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
            String update = "UPDATE Pessoas SET primeiroNome = ?, idade = ? WHERE id = ?";
            ps = conn.prepareStatement(update);
            ps.setString(1, pessoa.getPrimeiroNome());
            ps.setInt(2, pessoa.getIdade());
            ps.setInt(3, pessoa.getId());
            
            if (ps.execute()) {
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
            String select = "SELECT * FROM pessoas";
            ps = conn.prepareStatement(select);
            
            ResultSet rs = ps.getResultSet();
            
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
            String select = "SELECT * FROM pessoas WHERE id = ?";
            ps = conn.prepareStatement(select);
            ps.setInt(1, id);
            ResultSet rs = ps.getResultSet();
            
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
