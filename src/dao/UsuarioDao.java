package dao;

import beans.Usuario;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDao {

    // Atributo de conexão
     private final Conexao conexao;

    public UsuarioDao(Conexao conexao) {
        this.conexao = conexao;
    }

    public Usuario validarUsuario(Usuario user) {

        String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";

        try (Connection conn = conexao.connectDB(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getLogin());
            ps.setString(2, user.getSenha());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuarioEncontrado = new Usuario();
                    usuarioEncontrado.setId(rs.getInt("id"));
                    usuarioEncontrado.setLogin(rs.getString("login"));
                    usuarioEncontrado.setSenha(rs.getString("senha"));
                    usuarioEncontrado.setTipo(rs.getString("tipo"));
                    return usuarioEncontrado;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao validar usuário: " + e.getMessage());
        }

        return null;
    }

}
