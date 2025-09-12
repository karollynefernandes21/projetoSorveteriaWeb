package dao;

import beans.Vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class VendasDao {

    // Atributo para conexão
    private final Connection conn;

    public VendasDao(Connection conn) {
        this.conn = conn;
    }

    // Método para armazenar uma venda
    public void salvarVenda(Vendas venda) {
        String sql = "INSERT INTO vendas (sorvete_id, valor_total, cpf_cliente, forma_pagamento, quantidade_sorvete, data_hora) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, venda.getIdSorvete());
            ps.setDouble(2, venda.getValorTotal());
            ps.setString(3, venda.getCpfCliente());
            ps.setString(4, venda.getFormaPagamento());
            ps.setInt(5, venda.getQuantidadeSorvete());
            ps.setTimestamp(6, Timestamp.valueOf(venda.getDataHoraVenda()));

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao salvar venda: " + e.getMessage());
        }
    }

    // Método para buscar a quantidade no estoque pelo id
    public int buscarEstoquePorId(int idSorvete) {

        int estoque = 0;
        String sql = "SELECT quantidade FROM sorvetes WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSorvete);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                estoque = rs.getInt("quantidade");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar estoque por id: " + e.getMessage());
            e.printStackTrace();
        }

        return estoque;
    }

    // Método para atualizar estoque
    public void atualizarEstoque(int idSorvete, int novaQuantidade) {
        String sql = "UPDATE sorvetes SET quantidade = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, novaQuantidade);
            ps.setInt(2, idSorvete);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar estoque: " + e.getMessage());
        }
    }

    // Método para listar as vendas
    public ArrayList<Vendas> listarVendas() {
        ArrayList<Vendas> listagem = new ArrayList<>();
        String sql = "SELECT * FROM vendas";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            // Inserindo nova venda na lista
            while (rs.next()) {
                listagem.add(criarVenda(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar vendas: " + e.getMessage());
        }

        return listagem;
    }

    // Método para buscar vendas por id
    public Vendas buscarVendasPorId(int id) {
        String sql = "SELECT * FROM vendas WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return criarVenda(rs);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar vendas por id: " + e.getMessage());
        }
        return null;
    }

    // Método para consultar vendas por período
    public ArrayList<Vendas> consultarVendasPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {

        ArrayList<Vendas> listagem = new ArrayList<>();
        String sql = "SELECT * FROM vendas WHERE data_hora BETWEEN ? AND ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(dataInicial));
            ps.setTimestamp(2, Timestamp.valueOf(dataFinal));

            ResultSet rs = ps.executeQuery();

            // Inserindo nova venda na lista
            while (rs.next()) {
                listagem.add(criarVenda(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar vendas por período: " + e.getMessage());
        }

        return listagem;
    }

    // Método para armazenar lógica de criação de vendas
    private Vendas criarVenda(ResultSet rs) throws SQLException {

        Vendas venda = new Vendas();

        venda.setId(rs.getInt("id"));
        venda.setIdSorvete(rs.getInt("sorvete_id"));
        venda.setValorTotal(rs.getDouble("valor_total"));
        venda.setCpfCliente(rs.getString("cpf_cliente"));
        venda.setFormaPagamento(rs.getString("forma_pagamento"));
        venda.setQuantidadeSorvete(rs.getInt("quantidade_sorvete"));
        venda.setDataHoraVenda(rs.getTimestamp("data_hora").toLocalDateTime());

        return venda;
    }

}
