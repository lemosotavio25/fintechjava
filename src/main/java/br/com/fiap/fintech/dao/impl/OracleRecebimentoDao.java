package br.com.fiap.fintech.dao.impl;

import br.com.fiap.fintech.dao.RecebimentoDao;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.model.Recebimento;
import br.com.fiap.fintech.dao.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;


public class OracleRecebimentoDao implements RecebimentoDao {

    @Override
    public void cadastrar(Recebimento recebimento) throws DBException {
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO TB_RECEBIMENTOS (ID_RECEBIMENTO, DES_RECEBIMENTO, VAL_RECEBIMENTO, DAT_RECEBIMENTO, ID_CONTA) " +
                    "VALUES (SEQ_TB_RECEBIMENTOS.NEXTVAL, ?, ?, ?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, recebimento.getDescricao());
            stmt.setDouble(2, recebimento.getValorRecebimento());
            stmt.setDate(3, new java.sql.Date(recebimento.getDataRecebimento().getTime()));
            stmt.setInt(4, recebimento.getIdConta());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao cadastrar recebimento", e);
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
    public Recebimento buscar(int id) {
        Recebimento recebimento = null;
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM TB_RECEBIMENTOS WHERE ID_RECEBIMENTO = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                recebimento = new Recebimento(
                        rs.getInt("ID_RECEBIMENTO"),
                        rs.getString("DES_RECEBIMENTO"),
                        rs.getDouble("VAL_RECEBIMENTO"),
                        rs.getDate("DAT_RECEBIMENTO"),
                        rs.getInt("ID_CONTA")
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
        return recebimento;
    }

    @Override
    public void atualizar(Recebimento recebimento) throws DBException {
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE TB_RECEBIMENTOS SET DES_RECEBIMENTO = ?, VAL_RECEBIMENTO = ?, DAT_RECEBIMENTO = ?, ID_CONTA = ? WHERE ID_RECEBIMENTO = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, recebimento.getDescricao());
            stmt.setDouble(2, recebimento.getValorRecebimento());
            stmt.setDate(3, new java.sql.Date(recebimento.getDataRecebimento().getTime()));
            stmt.setInt(4, recebimento.getIdConta());
            stmt.setInt(5, recebimento.getIdRecebimento());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar recebimento", e);
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
            String sql = "DELETE FROM TB_RECEBIMENTOS WHERE ID_RECEBIMENTO = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao remover recebimento", e);
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
    public List<Recebimento> listar() {
        List<Recebimento> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM TB_RECEBIMENTOS";
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Recebimento recebimento = new Recebimento(
                        rs.getInt("ID_RECEBIMENTO"),
                        rs.getString("DES_RECEBIMENTO"),
                        rs.getDouble("VAL_RECEBIMENTO"),
                        rs.getDate("DAT_RECEBIMENTO"),
                        rs.getInt("ID_CONTA")
                );
                lista.add(recebimento);
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
    public List<Recebimento> listarRecebimentosPorConta(int idConta) {
        List<Recebimento> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 1. Conexão com o banco de dados
            conexao = ConnectionManager.getInstance().getConnection();

            // 2. SQL para listar recebimentos por conta
            String sql = "SELECT * FROM TB_RECEBIMENTOS WHERE ID_CONTA = ?";

            // 3. Preparar o statement e definir o valor do ID da conta
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idConta);

            // 4. Executar a query e processar os resultados
            rs = stmt.executeQuery();
            while (rs.next()) {
                Recebimento recebimento = new Recebimento(
                        rs.getInt("ID_RECEBIMENTO"),
                        rs.getString("DES_RECEBIMENTO"),
                        rs.getDouble("VAL_RECEBIMENTO"),
                        rs.getDate("DAT_RECEBIMENTO"),
                        rs.getInt("ID_CONTA")
                );
                lista.add(recebimento);  // Adicionar o recebimento à lista
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

    public List<Recebimento> listarPorMes(int idConta, String mes) {
        List<Recebimento> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();

            // Query para buscar pelo mês. Adaptar dependendo do banco de dados, no caso aqui para Oracle.
            String sql = "SELECT * FROM TB_RECEBIMENTOS WHERE ID_CONTA = ? AND TO_CHAR(DAT_RECEBIMENTO, 'MM') = ?";

            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idConta);
            stmt.setString(2, mes); // '01' para janeiro, '02' para fevereiro, etc.

            rs = stmt.executeQuery();

            while (rs.next()) {
                Recebimento recebimento = new Recebimento(
                        rs.getInt("ID_RECEBIMENTO"),
                        rs.getString("DES_RECEBIMENTO"),
                        rs.getDouble("VAL_RECEBIMENTO"),
                        rs.getDate("DAT_RECEBIMENTO"),
                        rs.getInt("ID_CONTA")
                );
                lista.add(recebimento);
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

    public Map<String, Double> listarTotalRecebimentosPorMes(int idConta) {
        Map<String, Double> totaisPorMes = new LinkedHashMap<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();

            // Query para agrupar e somar os recebimentos por mês
            String sql = "SELECT TO_CHAR(DAT_RECEBIMENTO, 'MM') AS mes, SUM(VAL_RECEBIMENTO) AS total " +
                    "FROM TB_RECEBIMENTOS WHERE ID_CONTA = ? " +
                    "GROUP BY TO_CHAR(DAT_RECEBIMENTO, 'MM') " +
                    "ORDER BY mes";

            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idConta);

            rs = stmt.executeQuery();

            while (rs.next()) {
                String mes = rs.getString("mes"); // Mês como string '01', '02', etc.
                Double total = rs.getDouble("total"); // Total dos recebimentos do mês
                totaisPorMes.put(mes, total);
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
        return totaisPorMes;
    }

}
