package br.com.fiap.fintech.factory;

import br.com.fiap.fintech.dao.ContaDao;
import br.com.fiap.fintech.dao.DespesaDao;
import br.com.fiap.fintech.dao.InvestimentoDao;
import br.com.fiap.fintech.dao.RecebimentoDao;
import br.com.fiap.fintech.dao.UsuarioDao;
import br.com.fiap.fintech.dao.impl.OracleContaDao;
import br.com.fiap.fintech.dao.impl.OracleDespesaDao;
import br.com.fiap.fintech.dao.impl.OracleInvestimentoDao;
import br.com.fiap.fintech.dao.impl.OracleRecebimentoDao;
import br.com.fiap.fintech.dao.impl.OracleUsuarioDao;

public class DaoFactory {

    // Factory para UsuarioDao
    public static UsuarioDao getUsuarioDao() {
        return new OracleUsuarioDao();
    }

    // Factory para ContaDao
    public static ContaDao getContaDao() {
        return new OracleContaDao();
    }

    // Factory para InvestimentoDao
    public static InvestimentoDao getInvestimentoDao() {
        return new OracleInvestimentoDao();
    }

    // Factory para RecebimentoDao
    public static RecebimentoDao getRecebimentoDao() {
        return new OracleRecebimentoDao();
    }

    // Factory para DespesaDao
    public static DespesaDao getDespesaDao() {
        return new OracleDespesaDao();
    }
}
