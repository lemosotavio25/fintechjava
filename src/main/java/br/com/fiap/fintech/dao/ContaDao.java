package br.com.fiap.fintech.dao;

import br.com.fiap.fintech.model.Conta;
import br.com.fiap.fintech.exception.DBException;

import java.util.List;

public interface ContaDao {
    void cadastrar(Conta conta) throws DBException;
    Conta buscar(int id);
    void atualizar(Conta conta) throws DBException;
    void remover(int id) throws DBException;
    List<Conta> listar();
    Conta buscarPorIdUsuario(int idUsuario);
}
