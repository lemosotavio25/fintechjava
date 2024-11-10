package br.com.fiap.fintech.dao.impl;

import br.com.fiap.fintech.dao.DespesaDao;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.model.Despesa;
import br.com.fiap.fintech.dao.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

public class OracleDespesaDao implements DespesaDao {

    @Override
    public void cadastrar(Despesa despesa) throws DBException {
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO TB_DESPESAS (ID_DESPESA, DES_DESPESA, VAL_DESPESA, DAT_DESPESA, ID_CONTA) " +
                    "VALUES (SEQ_TB_DESPESAS.NEXTVAL, ?, ?, ?, ?)";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, despesa.getDescricao());
            stmt.setDouble(2, despesa.getValorDespesa());
            stmt.setDate(3, new java.sql.Date(despesa.getDataDespesa().getTime()));
            stmt.setInt(4, despesa.getIdConta());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao cadastrar despesa", e);
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
    public Despesa buscar(int id) {
        Despesa despesa = null;
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM TB_DESPESAS WHERE ID_DESPESA = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                despesa = new Despesa(
                        rs.getInt("ID_DESPESA"),
                        rs.getString("DES_DESPESA"),
                        rs.getDouble("VAL_DESPESA"),
                        rs.getDate("DAT_DESPESA"),
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
        return despesa;
    }

    @Override
    public void atualizar(Despesa despesa) throws DBException {
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE TB_DESPESAS SET DES_DESPESA = ?, VAL_DESPESA = ?, DAT_DESPESA = ?, ID_CONTA = ? WHERE ID_DESPESA = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, despesa.getDescricao());
            stmt.setDouble(2, despesa.getValorDespesa());
            stmt.setDate(3, new java.sql.Date(despesa.getDataDespesa().getTime()));
            stmt.setInt(4, despesa.getIdConta());
            stmt.setInt(5, despesa.getIdDespesa());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar despesa", e);
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
            String sql = "DELETE FROM TB_DESPESAS WHERE ID_DESPESA = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao remover despesa", e);
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
    public List<Despesa> listar() {
        List<Despesa> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM TB_DESPESAS";
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Despesa despesa = new Despesa(
                        rs.getInt("ID_DESPESA"),
                        rs.getString("DES_DESPESA"),
                        rs.getDouble("VAL_DESPESA"),
                        rs.getDate("DAT_DESPESA"),
                        rs.getInt("ID_CONTA")
                );
                lista.add(despesa);
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
    public List<Despesa> listarDespesasPorConta(int idConta) {
        List<Despesa> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM TB_DESPESAS WHERE ID_CONTA = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idConta);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Despesa despesa = new Despesa(
                        rs.getInt("ID_DESPESA"),
                        rs.getString("DES_DESPESA"),
                        rs.getDouble("VAL_DESPESA"),
                        rs.getDate("DAT_DESPESA"),
                        rs.getInt("ID_CONTA")
                );
                lista.add(despesa);
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

    public List<Despesa> listarPorMes(int idConta, String mes) {
        List<Despesa> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();

            // Query para buscar despesas pelo mês
            String sql = "SELECT * FROM TB_DESPESAS WHERE ID_CONTA = ? AND TO_CHAR(DAT_DESPESA, 'MM') = ?";

            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idConta);
            stmt.setString(2, mes); // '01' para janeiro, '02' para fevereiro, etc.

            rs = stmt.executeQuery();

            while (rs.next()) {
                Despesa despesa = new Despesa(
                        rs.getInt("ID_DESPESA"),
                        rs.getString("DES_DESPESA"),
                        rs.getDouble("VAL_DESPESA"),
                        rs.getDate("DAT_DESPESA"),
                        rs.getInt("ID_CONTA")
                );
                lista.add(despesa);
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

    public Map<String, Double> listarTotaisDespesasPorMes(int idConta) {
        Map<String, Double> despesasPorMes = new LinkedHashMap<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conexao = ConnectionManager.getInstance().getConnection();

            // Query que agrupa despesas pelo mês e calcula o total para cada mês
            String sql = "SELECT TO_CHAR(DAT_DESPESA, 'MM') AS mes, SUM(VAL_DESPESA) AS total " +
                    "FROM TB_DESPESAS " +
                    "WHERE ID_CONTA = ? " +
                    "GROUP BY TO_CHAR(DAT_DESPESA, 'MM') " +
                    "ORDER BY mes";

            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idConta);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String mes = rs.getString("mes");
                Double total = rs.getDouble("total");
                despesasPorMes.put(mes, total);
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
        return despesasPorMes;
    }

}
