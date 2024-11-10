package br.com.fiap.fintech.dao;

import br.com.fiap.fintech.model.Investimento;
import br.com.fiap.fintech.exception.DBException;

import java.util.List;

public interface InvestimentoDao {
    void cadastrar(Investimento investimento) throws DBException;
    Investimento buscar(int id);
    void atualizar(Investimento investimento) throws DBException;
    void remover(int id) throws DBException;
    List<Investimento> listar();
    List<Investimento> listarPorConta(int idConta);
}
