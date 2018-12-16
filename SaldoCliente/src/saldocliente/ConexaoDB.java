/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saldocliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author wellington
 */
public class ConexaoDB {
    
    
    String servidor = "servidor.wosilva.com:3306";
    String db = "db_financeiro";
    String url = "jdbc:mariadb://" + servidor + "/" + db;
    String usuario = "user_financeiro";
    String senha = "";
    Connection conexao;
       
    ConexaoDB(){ 
        
        try {
        
            conexao = DriverManager.getConnection(url, usuario, senha);
        
        } catch(SQLException e) {
        
            System.out.println(e);
        }
    }
    
}
