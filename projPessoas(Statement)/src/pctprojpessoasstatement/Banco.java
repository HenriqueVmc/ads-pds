package pctprojpessoasstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Banco {
	
	public Connection getConexao() {
		
		Connection conn = null;
		
		try{
	        Class.forName("com.mysql.jdbc.Driver");                        
	                  
	        try {//TESTE: Conectanto ao MySQL
	            conn = DriverManager.getConnection("jdbc:mysql://localhost/aulapds0708?autoReconnect=true&useSSL=false","root","aluno");	
	            //jdbc:mysql://localhost:3306/Peoples?autoReconnect=true&useSSL=false
	            
	        } catch (SQLException ex) {
	            Logger.getLogger(TesteBD.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }catch(ClassNotFoundException e){
	        e.printStackTrace();
	    }
		
		return conn;
	}
}
