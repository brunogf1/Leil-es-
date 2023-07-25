
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
public class conectaDAO {
    
    Connection conn;
    
 public boolean connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //conectando com o banco de dados, colocando o nome do banco, nome do usuário e a senha
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11", "root", "Azaza130705");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Ocorreu o seguinte erro:" + ex.getMessage());
            return false;
        }
    }
  public void desconectar() {
        try {
            //desconectando do banco de dados
            conn.close();
        } catch (SQLException ex) {

        }
    }
}
