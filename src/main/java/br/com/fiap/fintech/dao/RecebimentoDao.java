package br.com.fiap.fintech.dao;

import br.com.fiap.fintech.model.Recebimento;
import br.com.fiap.fintech.exception.DBException;

import java.util.List;
import java.util.Map;

public interface RecebimentoDao {
    void cadastrar(Recebimento recebimento) throws DBException;
    Recebimento buscar(int id);
    void atualizar(Recebimento recebimento) throws DBException;
    void remover(int id) throws DBException;
    List<Recebimento> listar();
    List<Recebimento> listarRecebimentosPorConta(int idConta);
    List<Recebimento> listarPorMes(int idConta, String mes);
    Map<String, Double> listarTotalRecebimentosPorMes(int idConta);
}
