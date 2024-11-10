package br.com.fiap.fintech.dao.impl;

import br.com.fiap.fintech.dao.ContaDao;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.model.Conta;
import br.com.fiap.fintech.dao.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleContaDao implements ContaDao {

    @Override
    public void cadastrar(Conta conta) throws DBException {
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();

            // Verifica se o usuário já possui uma conta
            String sqlVerifica = "SELECT COUNT(*) FROM TB_CONTAS WHERE ID_USUARIO = ?";
            stmt = conexao.prepareStatement(sqlVerifica);
            stmt.setInt(1, conta.getIdUsuario());
            rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                throw new DBException("Usuário já possui uma conta.");
            }

            // Caso contrário, cadastra a nova conta
            String sql = "INSERT INTO TB_CONTAS (ID_CONTA, BANCO, TIPO_CONTA, ID_USUARIO) VALUES (SEQ_TB_CONTAS.NEXTVAL, ?, ?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, conta.getBanco());
            stmt.setString(2, conta.getTipoConta());
            stmt.setInt(3, conta.getIdUsuario());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao cadastrar conta", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public Conta buscar(int id) {
        Conta conta = null;
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM TB_CONTAS WHERE ID_CONTA = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                conta = new Conta(
                        rs.getInt("ID_CONTA"),
                        rs.getString("BANCO"),
                        rs.getString("TIPO_CONTA"),
                        rs.getInt("ID_USUARIO")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conta;
    }

    @Override
    public void atualizar(Conta conta) throws DBException {
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE TB_CONTAS SET BANCO = ?, TIPO_CONTA = ?, ID_USUARIO = ? WHERE ID_CONTA = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, conta.getBanco());
            stmt.setString(2, conta.getTipoConta());
            stmt.setInt(3, conta.getIdUsuario());
            stmt.setInt(4, conta.getIdConta());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar conta", e);
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
    public void remover(int id) throws DBException {
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "DELETE FROM TB_CONTAS WHERE ID_CONTA = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao remover conta", e);
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
    public List<Conta> listar() {
        List<Conta> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM TB_CONTAS";
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Conta conta = new Conta(
                        rs.getInt("ID_CONTA"),
                        rs.getString("BANCO"),
                        rs.getString("TIPO_CONTA"),
                        rs.getInt("ID_USUARIO")
                );
                lista.add(conta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    @Override
    public Conta buscarPorIdUsuario(int idUsuario) {
        Conta conta = null;
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM TB_CONTAS WHERE ID_USUARIO = ?";  // Busca pela chave estrangeira ID_USUARIO
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            rs = stmt.executeQuery();

            if (rs.next()) {
                conta = new Conta(
                        rs.getInt("ID_CONTA"),
                        rs.getString("BANCO"),
                        rs.getString("TIPO_CONTA"),
                        rs.getInt("ID_USUARIO")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conta;
    }
}
