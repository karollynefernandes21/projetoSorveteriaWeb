package dao;

// Imports
import beans.Fornecedores;
import beans.Sorvetes;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SorvetesDao {

    // Atributo para conexão
    private final Conexao conexao;

    public SorvetesDao(Conexao conexao) {
        this.conexao = conexao;
    }

    // Método para cadastrar sorvetes
    public void cadastroSorvete(Sorvetes sorvete) {
        String sql = "INSERT INTO sorvetes (sabor_sorvete, descricao, categoria, unidade, quantidade, preco, data_entrada, data_validade, fornecedores_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.conexao.connectDB(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sorvete.getSaborSorvete());
            ps.setString(2, sorvete.getDescricao());
            ps.setString(3, sorvete.getCategoria());
            ps.setString(4, sorvete.getUnidade());
            ps.setInt(5, sorvete.getQuantidade());
            ps.setDouble(6, sorvete.getPreco());

            ps.setDate(7, java.sql.Date.valueOf(sorvete.getDataEntrada()));
            ps.setDate(8, java.sql.Date.valueOf(sorvete.getDataValidade()));

            ps.setInt(9, sorvete.getFornecedor().getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar sorvete: " + e.getMessage());
        }
    }

    // Método para buscar os sorvetes pelo nome
    public Sorvetes buscarSaborSorvete(String sabor) {

        String sql = "SELECT * FROM sorvetes WHERE sabor_sorvete LIKE ?";

        try (Connection conn = this.conexao.connectDB(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + sabor + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return criarSorvete(rs);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar sorvete: " + e.getMessage());
        }

        return null;
    }

    // Método para atualizar sorvetes
    public void atualizarSorvete(Sorvetes sorvete) {

        String sql = "UPDATE sorvetes SET sabor_sorvete = ?, descricao = ?, categoria = ?, unidade = ?, quantidade = ?, "
                + "preco = ?, fornecedores_id = ? WHERE id = ?";

        try (Connection conn = this.conexao.connectDB(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sorvete.getSaborSorvete());
            ps.setString(2, sorvete.getDescricao());
            ps.setString(3, sorvete.getCategoria());
            ps.setString(4, sorvete.getUnidade());
            ps.setInt(5, sorvete.getQuantidade());
            ps.setDouble(6, sorvete.getPreco());
            ps.setInt(7, sorvete.getFornecedor().getId());
            ps.setInt(8, sorvete.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar sorvete: " + e.getMessage());
        }
    }

    // Método para excluir sorvetes
    public void excluirSorvete(Sorvetes sorvete) {

        String sql = "DELETE FROM sorvetes WHERE id = ?";

        try (Connection conn = this.conexao.connectDB(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sorvete.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao excluir sorvete: " + e.getMessage());
        }
    }

    // Métodos para listar todos os sorvetes
    public ArrayList<Sorvetes> listarSorvetes() {
        ArrayList<Sorvetes> listagem = new ArrayList<>();
        String sql = "SELECT * FROM sorvetes";

        try (Connection conn = this.conexao.connectDB(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            // Adicionando sorvetes na lista
            while (rs.next()) {
                listagem.add(criarSorvete(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar sorvetes: " + e.getMessage());
        }

        return listagem;
    }

    // Método para listar os sorvetes filtrando pelo sabor
    public ArrayList<Sorvetes> listarSorvetesPorSabor(String sabor) {
        ArrayList<Sorvetes> listagem = new ArrayList<>();
        String sql = "SELECT * FROM sorvetes WHERE sabor_sorvete LIKE ?";

        try (Connection conn = this.conexao.connectDB(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + sabor + "%");

            ResultSet rs = ps.executeQuery();

            // Adicionando sorvetes na lista
            while (rs.next()) {
                listagem.add(criarSorvete(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar sorvetes por sabor: " + e.getMessage());
        }

        return listagem;
    }

    // Método para armazenar a criação de sorvetes
    private Sorvetes criarSorvete(ResultSet rs) throws SQLException {

        Sorvetes sorvete = new Sorvetes();
        FornecedoresDao fornecedoresDao = new FornecedoresDao();

        sorvete.setId(rs.getInt("id"));
        sorvete.setSaborSorvete(rs.getString("sabor_sorvete"));
        sorvete.setDescricao(rs.getString("descricao"));
        sorvete.setCategoria(rs.getString("categoria"));
        sorvete.setUnidade(rs.getString("unidade"));
        sorvete.setQuantidade(rs.getInt("quantidade"));
        sorvete.setPreco(rs.getDouble("preco"));
        sorvete.setDataEntrada(rs.getDate("data_entrada").toLocalDate());
        sorvete.setDataValidade(rs.getDate("data_validade").toLocalDate());

        int fornecedorId = rs.getInt("fornecedores_id");
        Fornecedores fornecedor = fornecedoresDao.buscarFornecedorPorId(fornecedorId);
        sorvete.setFornecedor(fornecedor);
        
        return sorvete;
    }
}
