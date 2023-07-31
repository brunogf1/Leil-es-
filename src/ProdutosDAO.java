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
import java.util.List;

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
    
    public List<ProdutosDTO> listarProdutos() {
        String sql = "select * from produtos";

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            List<ProdutosDTO> lista = new ArrayList();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                lista.add(produto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println("Ocorreu o seguinte erro: " + ex.getMessage());
            return null;
        }
      
    }
    
    public List<ProdutosDTO> listarProdutosVendidos(String filtro) {
        String sql = "select * from produtos";

        if (!filtro.isEmpty()) {
            //filtro para mostrar dados da categoria filtrada
            sql = sql + " where status like ?";
        }

        try {
            prep = conn.prepareStatement(sql);

            if (!filtro.isEmpty()) {
                prep.setString(1,"%"+ filtro+"%");
            }

            resultset = prep.executeQuery();
            List<ProdutosDTO> lista = new ArrayList();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                lista.add(produto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println("Ocorreu o seguinte erro: " + ex.getMessage());
            return null;
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
