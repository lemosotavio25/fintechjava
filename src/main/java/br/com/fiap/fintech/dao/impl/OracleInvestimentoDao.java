package br.com.fiap.fintech.dao.impl;

import br.com.fiap.fintech.dao.InvestimentoDao;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.model.Investimento;
import br.com.fiap.fintech.dao.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleInvestimentoDao implements InvestimentoDao {

    @Override
    public void cadastrar(Investimento investimento) throws DBException {
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "INSERT INTO TB_INVESTIMENTOS (ID_INVESTIMENTO, TIPO_INVESTIMENTO, VALOR_INVESTIDO, RENTABILIDADE, DATA_INVESTIMENTO, DATA_RESGATE, ID_CONTA) " +
                    "VALUES (SEQ_TB_INVESTIMENTOS.NEXTVAL, ?, ?, ?, ?, ?, ?)";
            stmt = conexao.prepareStatement(sql);

            stmt.setString(1, investimento.getTipoInvestimento());
            stmt.setDouble(2, investimento.getValorInvestido());
            stmt.setDouble(3, investimento.getRentabilidade());
            stmt.setDate(4, new java.sql.Date(investimento.getDataInvestimento().getTime()));

            // Verificação de nulo para `dataResgate`
            if (investimento.getDataResgate() != null) {
                stmt.setDate(5, new java.sql.Date(investimento.getDataResgate().getTime()));
            } else {
                stmt.setNull(5, java.sql.Types.DATE);
            }

            stmt.setInt(6, investimento.getIdConta());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao cadastrar investimento", e);
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
    public Investimento buscar(int id) {
        Investimento investimento = null;
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM TB_INVESTIMENTOS WHERE ID_INVESTIMENTO = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                investimento = new Investimento(
                        rs.getInt("ID_INVESTIMENTO"),
                        rs.getString("TIPO_INVESTIMENTO"),
                        rs.getDouble("VALOR_INVESTIDO"),
                        rs.getDouble("RENTABILIDADE"),
                        rs.getDate("DATA_INVESTIMENTO"),
                        rs.getDate("DATA_RESGATE"),
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
        return investimento;
    }

    @Override
    public void atualizar(Investimento investimento) throws DBException {
        Connection conexao = null;
        PreparedStatement stmt = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "UPDATE TB_INVESTIMENTOS SET TIPO_INVESTIMENTO = ?, VALOR_INVESTIDO = ?, RENTABILIDADE = ?, DATA_INVESTIMENTO = ?, DATA_RESGATE = ?, ID_CONTA = ? WHERE ID_INVESTIMENTO = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setString(1, investimento.getTipoInvestimento());
            stmt.setDouble(2, investimento.getValorInvestido());
            stmt.setDouble(3, investimento.getRentabilidade());
            stmt.setDate(4, new java.sql.Date(investimento.getDataInvestimento().getTime()));
            stmt.setDate(5, new java.sql.Date(investimento.getDataResgate().getTime()));
            stmt.setInt(6, investimento.getIdConta());
            stmt.setInt(7, investimento.getIdInvestimento());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao atualizar investimento", e);
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
            String sql = "DELETE FROM TB_INVESTIMENTOS WHERE ID_INVESTIMENTO = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Erro ao remover investimento", e);
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
    public List<Investimento> listar() {
        List<Investimento> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM TB_INVESTIMENTOS";
            stmt = conexao.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Investimento investimento = new Investimento(
                        rs.getInt("ID_INVESTIMENTO"),
                        rs.getString("TIPO_INVESTIMENTO"),
                        rs.getDouble("VALOR_INVESTIDO"),
                        rs.getDouble("RENTABILIDADE"),
                        rs.getDate("DATA_INVESTIMENTO"),
                        rs.getDate("DATA_RESGATE"),
                        rs.getInt("ID_CONTA")
                );
                lista.add(investimento);
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
    public List<Investimento> listarPorConta(int idConta) {
        List<Investimento> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conexao = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM TB_INVESTIMENTOS WHERE ID_CONTA = ?";
            stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, idConta);  // Define o valor do idConta na consulta
            rs = stmt.executeQuery();

            while (rs.next()) {
                Investimento investimento = new Investimento(
                        rs.getInt("ID_INVESTIMENTO"),
                        rs.getString("TIPO_INVESTIMENTO"),
                        rs.getDouble("VALOR_INVESTIDO"),
                        rs.getDouble("RENTABILIDADE"),
                        rs.getDate("DATA_INVESTIMENTO"),
                        rs.getDate("DATA_RESGATE"),
                        rs.getInt("ID_CONTA")
                );
                lista.add(investimento);
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

}
