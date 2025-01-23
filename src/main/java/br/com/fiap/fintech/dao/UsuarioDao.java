package br.com.fiap.fintech.dao;

import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.model.Usuario;

public interface UsuarioDao {
    boolean validarUsuario(Usuario usuario) throws DBException;;
    void cadastrar(Usuario usuario) throws DBException;
    Usuario buscarPorEmail(String email) throws DBException;

}
