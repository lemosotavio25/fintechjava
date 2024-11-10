package br.com.fiap.fintech.dao;

import br.com.fiap.fintech.model.Despesa;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.model.Recebimento;

import java.util.List;
import java.util.Map;

public interface DespesaDao {
    void cadastrar(Despesa despesa) throws DBException;
    Despesa buscar(int id);
    void atualizar(Despesa despesa) throws DBException;
    void remover(int id) throws DBException;
    List<Despesa> listar();
    List<Despesa> listarDespesasPorConta(int idConta);
    List<Despesa> listarPorMes(int idConta, String mes);
    Map<String, Double> listarTotaisDespesasPorMes(int idConta);
}
