package br.com.fiap.fintech.teste;

import br.com.fiap.fintech.dao.impl.OracleUsuarioDao;
import br.com.fiap.fintech.model.Usuario;

public class UsuarioTeste {

    public static void main(String[] args) {
        // Criar uma instância da DAO
        OracleUsuarioDao usuarioDao = new OracleUsuarioDao();

        // 1. Teste de criação de um usuário
        Usuario novoUsuario = new Usuario("joao.silva@email.com", "1234");
        novoUsuario.setNome("João Silva");

        try {
            // Cadastrar o novo usuário
            usuarioDao.cadastrar(novoUsuario);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao cadastrar usuário.");
        }

        // 2. Teste de validação de login
        Usuario usuarioLogin = new Usuario("joao.silva@email.com", "1234");
        boolean loginValido = usuarioDao.validarUsuario(usuarioLogin);

        if (loginValido) {
            System.out.println("Login realizado com sucesso!");
        } else {
            System.out.println("Falha no login: usuário ou senha inválidos.");
        }
    }
}
