package br.com.fiap.fintech.teste;

import br.com.fiap.fintech.dao.UsuarioDao;
import br.com.fiap.fintech.factory.DaoFactory;
import br.com.fiap.fintech.model.Usuario;
import br.com.fiap.fintech.exception.DBException;

public class UsuarioTestes {

    public static void main(String[] args) {
        buscarUsuarioPorEmailTeste();
    }

    public static void buscarUsuarioPorEmailTeste() {
        try {
            // Instanciar o UsuarioDao
            UsuarioDao usuarioDao = DaoFactory.getUsuarioDao();

            // Definir um email existente para buscar no banco
            String email = "joao.silva@email.com";

            // Buscar o usuário pelo email
            Usuario usuario = usuarioDao.buscarPorEmail(email);

            // Verificar se o usuário foi encontrado
            if (usuario != null) {
                System.out.println("Usuário encontrado:");
                System.out.println("ID: " + usuario.getId());
                System.out.println("Nome: " + usuario.getNome());
                System.out.println("Email: " + usuario.getEmail());
            } else {
                System.out.println("Usuário não encontrado para o email: " + email);
            }
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
