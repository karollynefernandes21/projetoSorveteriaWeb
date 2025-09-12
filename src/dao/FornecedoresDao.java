package dao;

// Imports
import beans.Fornecedores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FornecedoresDao {

    // Atributo para conexão
    private Connection conn;

    public FornecedoresDao(Connection conn) {
        this.conn = conn;
    }
    
    public FornecedoresDao(){}

    // Método para cadastrar fornecedores
    public void cadastroFornecedores(Fornecedores fornecedor) {

        String sql = "INSERT INTO fornecedores (nome, cnpj, email, celular, cep, endereco, numero, bairro, cidade, complemento, unidade) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fornecedor.getNome());
            ps.setString(2, fornecedor.getCnpj());
            ps.setString(3, fornecedor.getEmail());
            ps.setString(4, fornecedor.getCelular());
            ps.setString(5, fornecedor.getCep());
            ps.setString(6, fornecedor.getEndereco());
            ps.setString(7, fornecedor.getNumero());
            ps.setString(8, fornecedor.getBairro());
            ps.setString(9, fornecedor.getCidade());
            ps.setString(10, fornecedor.getComplemento());
            ps.setString(11, fornecedor.getUnidade());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar fornecedor: " + e.getMessage());
        }
    }

    // Método para buscar os fornecedores pelo nome
    public Fornecedores buscarNomeFornecedor(String nome) {

        String sql = "SELECT * FROM fornecedores WHERE nome LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nome + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return criarFornecedores(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao pesquisar fornecedor: " + e.getMessage());
        }
        return null;
    }

    // Método para atualizar os fornecedores
    public void atualizarFornecedores(Fornecedores fornecedor) {

        String sql = "UPDATE fornecedores SET nome = ?, cnpj = ?, email = ?, celular = ?, cep = ?, endereco = ?, "
                + "numero = ?, bairro = ?, cidade = ?, complemento = ?, unidade = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fornecedor.getNome());
            ps.setString(2, fornecedor.getCnpj());
            ps.setString(3, fornecedor.getEmail());
            ps.setString(4, fornecedor.getCelular());
            ps.setString(5, fornecedor.getCep());
            ps.setString(6, fornecedor.getEndereco());
            ps.setString(7, fornecedor.getNumero());
            ps.setString(8, fornecedor.getBairro());
            ps.setString(9, fornecedor.getCidade());
            ps.setString(10, fornecedor.getComplemento());
            ps.setString(11, fornecedor.getUnidade());
            ps.setInt(12, fornecedor.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar fornecedor: " + e.getMessage());
        }
    }

    // Método para excluir os fornecedores
    public void excluirFornecedores(Fornecedores fornecedor) {

        String sql = "DELETE FROM fornecedores WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, fornecedor.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao excluir fornecedor: " + e.getMessage());
        }
    }

    // Método para listar todos os fornecedores
    public ArrayList<Fornecedores> listarFornecedores() {

        ArrayList<Fornecedores> listagem = new ArrayList<>();
        String sql = "SELECT * FROM fornecedores";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            // Inserindo fornecedores na lista
            while (rs.next()) {
                listagem.add(criarFornecedores(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar fornecedor: " + e.getMessage());
        }
        return listagem;
    }

    // Método para listar os fornecedores filtrando pelo nome
    public ArrayList<Fornecedores> listarFornecedoresPorNome(String nome) {
        ArrayList<Fornecedores> listagem = new ArrayList<>();
        String sql = "SELECT * FROM fornecedores WHERE nome LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nome + "%");

            ResultSet rs = ps.executeQuery();

            // Inserindo fornecedores na lista
            while (rs.next()) {
                listagem.add(criarFornecedores(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar fornecedores por nome: " + e.getMessage());
        }

        return listagem;
    }

    // Método para buscar os fornecedores pelo id
    public Fornecedores buscarFornecedorPorId(int id) {
        String sql = "SELECT * FROM fornecedores WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return criarFornecedores(rs);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar fornecedor: " + e.getMessage());
        }

        return null;
    }

    // Método para armazenar lógica de criação de fornecedores
    private Fornecedores criarFornecedores(ResultSet rs) throws SQLException {

        Fornecedores fornecedor = new Fornecedores();

        fornecedor.setId(rs.getInt("id"));
        fornecedor.setNome(rs.getString("nome"));
        fornecedor.setCnpj(rs.getString("cnpj"));
        fornecedor.setEmail(rs.getString("email"));
        fornecedor.setCelular(rs.getString("celular"));
        fornecedor.setCep(rs.getString("cep"));
        fornecedor.setEndereco(rs.getString("endereco"));
        fornecedor.setNumero(rs.getString("numero"));
        fornecedor.setBairro(rs.getString("bairro"));
        fornecedor.setCidade(rs.getString("cidade"));
        fornecedor.setComplemento(rs.getString("complemento"));
        fornecedor.setUnidade(rs.getString("unidade"));

        return fornecedor;
    }
}
