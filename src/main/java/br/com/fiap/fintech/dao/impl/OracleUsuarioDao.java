package br.com.fiap.fintech.dao.impl;

import br.com.fiap.fintech.dao.ConnectionManager;
import br.com.fiap.fintech.dao.UsuarioDao;
import br.com.fiap.fintech.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleUsuarioDao implements UsuarioDao {

    private Connection conexao;

    @Override
    public boolean validarUsuario(Usuario usuario) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager
                    .getInstance()
                    .getConnection();

            // SQL atualizada para corresponder aos nomes de colunas corretos
            String sql = "SELECT * FROM TB_USUARIOS WHERE EMAIL = ? AND SENHA = ?";

            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }



    @Override
    public void cadastrar(Usuario usuario) {
        PreparedStatement stmt = null;
        try {
            conexao = ConnectionManager
                    .getInstance()
                    .getConnection();

            // SQL atualizada para corresponder aos nomes de colunas corretos
            String sql = "INSERT INTO TB_USUARIOS (ID_USUARIO, NM_USUARIO, EMAIL, SENHA) VALUES (SEQ_TB_USUARIO.NEXTVAL, ?, ?, ?)";

            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        Usuario usuario = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 1. Estabelecer a conexão com o banco de dados
            conexao = ConnectionManager.getInstance().getConnection();

            // 2. Definir a query SQL para buscar o usuário pelo email
            String sql = "SELECT * FROM TB_USUARIOS WHERE EMAIL = ?";

            // 3. Preparar o statement
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);  // Definir o valor do email no statement

            // 4. Executar a query
            rs = stmt.executeQuery();

            // 5. Se houver resultado, preencher o objeto Usuario
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("ID_USUARIO"));
                usuario.setNome(rs.getString("NM_USUARIO"));
                usuario.setEmail(rs.getString("EMAIL"));
                usuario.setSenha(rs.getString("SENHA"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 6. Fechar recursos (ResultSet, PreparedStatement, Connection)
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;  // Retorna o objeto Usuario ou null se não encontrado
    }


}
