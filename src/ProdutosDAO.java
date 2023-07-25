/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
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
    
    
    public int cadastrarProduto(ProdutosDTO produto) {
        int status;
        try {
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES(?,?,?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            status = prep.executeUpdate();
            return status;
        } catch (SQLException ex) {
            System.out.println("Ocorreu o seguinte erro:" + ex.getMessage());
            return ex.getErrorCode();
        }

    }

      public void desconectar() {
        try {
            //desconectando do banco de dados
            conn.close();
        } catch (SQLException ex) {

        }
    }
    
    
    public ArrayList<ProdutosDTO> listarProdutos() {

        return listagem;
    }

}
