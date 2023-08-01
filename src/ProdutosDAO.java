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

   public Connection connectDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Conectando com o banco de dados, colocando o nome do banco, nome do usuário e a senha
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11", "root", "Azaza130705");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Ocorreu o seguinte erro:" + ex.getMessage());
            return null;
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
    
    public void venderProduto(String idProduto) {
        String sql = "UPDATE produtos SET status = 'vendido' WHERE id = ?";

        try {
            // Obtém a conexão com o banco de dados usando o método connectDB
            conn = connectDB();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idProduto);
            stmt.executeUpdate();
            // Fecha o statement após o uso
            stmt.close();
        } catch (SQLException e) {
            // Trate os erros de forma adequada para sua aplicação
            e.printStackTrace();
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
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
