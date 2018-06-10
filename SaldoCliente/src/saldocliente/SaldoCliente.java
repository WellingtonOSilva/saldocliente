/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saldocliente;

import static java.lang.Float.max;
import static java.lang.Float.min;
import java.sql.ResultSet;
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
    private static ResultSet rs;
    
    public static void main(String[] args) throws SQLException {
        
        // Insere registros com dados randômicos para testes
        //insereRegistros();
        
        saldoCliente();
        mediaSaldo(); 
       
        // Apaga os dados da tabela, opcional
        //truncate();
      
    }
    
    public static void insereRegistros() {
        
        System.out.println("Inserindo registros...");
        System.out.println("Por favor, aguarde!");

        while(dadosAleatorios()) {
            System.out.println("Registros inseridos com sucesso");
            break;
        } 
        
    }
    
    public static Boolean dadosAleatorios(){
    
        ConexaoDB db = new ConexaoDB();

        try {
                  
            for(int i = 0; i < 3000; i++) {
                 
                char[] chars = "0123456789".toCharArray();
                StringBuilder sb = new StringBuilder(11);
                Random random = new Random();
                for (int a = 0; a < 11; a++) {
                    char c = chars[random.nextInt(chars.length)];
                    sb.append(c);
            }
            
            String cpf = sb.toString();
                
            double vl_total =  ThreadLocalRandom.current().nextInt(0, 9999) * 1.33;
                                               
            stmt = db.conexao.createStatement();
            sql = "insert tb_customer_account (cpf_cnpj, nm_costumer, is_active, vl_total) values ('" + cpf + "', 'Fulano', 1, "+ vl_total +")";
            stmt.execute(sql);
           
        } 
            
            db.conexao.close();
        
        }   catch (SQLException e) {
            
            System.out.println(e);
            
        }
             
        return true;
    }
    
    //Imprime o saldo do cliente que antender aos requisitos 
    public static void saldoCliente() {
        
        System.out.println("Consultando os dados");
        
        System.out.format("+-------+-----------------+----------------+-----------+%n");
        System.out.format("|ID     | Nome            | CPF - CNPJ     | Saldo     |%n");
        System.out.format("+-------+-----------------+----------------+-----------+%n");
        
        try {
            ConexaoDB db = new ConexaoDB();
            stmt = db.conexao.createStatement();
            rs = stmt.executeQuery("select id_costumer, nm_costumer, cpf_cnpj, vl_total  from tb_customer_account where id_costumer between 1501 and 2699 and vl_total > 560 ORDER BY vl_total DESC");
            
            String formatacao = "| %-5s | %-15s | %-14s | %-9s |%n";

            
            while(rs.next()) {
                String nm_costumer = rs.getString("nm_costumer");
                String cpf_cnpj = rs.getString("cpf_cnpj");
                float vl_total = rs.getFloat("vl_total");
                
                System.out.format(formatacao, rs.getInt("id_costumer"),nm_costumer, rs.getString("cpf_cnpj"), String.format("%.2f", rs.getFloat("vl_total")));
            }
            
            System.out.format("+-------+-----------------+----------------+-----------+%n");

        
        } catch (SQLException e) {
            
            System.out.println(e);
        }
    }
   
    //Calcula a média do saldo dos clientes que cumpriram os requisistos do procedimento saldoCliente()
    public static void mediaSaldo() {
        
               try {
            ConexaoDB db = new ConexaoDB();
            stmt = db.conexao.createStatement();
            rs = stmt.executeQuery("select sum(vl_total) / count(id_costumer) as media from tb_customer_account where id_costumer between 1501 and 1699 and vl_total > 560");
            
            while(rs.next()) {
                             
                System.out.println("A media de saldo desses clientes é de: " + String.format("%.2f", rs.getFloat("media")));
            }
        
        } catch (SQLException e) {
            
            System.out.println(e);
        }
    
    }
    
    //Exclui os dados da tabela
    private static void truncate() {
        
        try {
            ConexaoDB db = new ConexaoDB();
            
            stmt = db.conexao.createStatement();
            stmt.execute("truncate tb_customer_account");
            
        } catch (SQLException e) {
            
            System.out.println(e);
        }
    }
}
