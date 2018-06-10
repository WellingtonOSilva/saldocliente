/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saldocliente;

import static java.lang.Float.max;
import static java.lang.Float.min;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author wellington
 */
public class SaldoCliente {

    /**
     * @param args the command line arguments
     */
    
    private static Statement stmt;
    private static String sql;
    
    public static void main(String[] args) throws SQLException {
        // TODO code application logic here
        System.out.println("Inserindo registros...");
        
        while(insereRegistros()) {
            System.out.println("Registros inseridos com sucesso");
            break;
        }
       
      
        
    }
    
    public static Boolean insereRegistros() throws SQLException {
    
        ConexaoDB db = new ConexaoDB();


        
        for(int i = 0; i < 10; i++) {
        
            try {
                
                int cpfInt = ThreadLocalRandom.current().nextInt(100000000, 999999999);
                int digito = ThreadLocalRandom.current().nextInt(10, 99);
                
                double vl_total =  ThreadLocalRandom.current().nextInt(0, 9999) * 1.33;
                                               
                String cpf = Integer.toString(cpfInt) + Integer.toString(digito);
                
                stmt = db.conexao.createStatement();
                
                sql = "insert tb_customer_account (cpf_cnpj, nm_costumer, is_active, vl_total) values ('" + cpf + "', 'Fulano', 1, "+ vl_total +")";
                
                stmt.execute(sql);
           
            } catch (SQLException e) {
                System.out.println(e);
            }
            
        }
        
        db.conexao.close();
        
        return true;
    }
    
}
