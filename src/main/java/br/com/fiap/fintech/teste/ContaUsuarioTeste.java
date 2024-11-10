package br.com.fiap.fintech.teste;

import br.com.fiap.fintech.dao.UsuarioDao;
import br.com.fiap.fintech.dao.ContaDao;
import br.com.fiap.fintech.dao.RecebimentoDao;
import br.com.fiap.fintech.factory.DaoFactory;
import br.com.fiap.fintech.model.Usuario;
import br.com.fiap.fintech.model.Conta;
import br.com.fiap.fintech.model.Recebimento;

import java.util.List;

public class ContaUsuarioTeste {

    public static void main(String[] args) {
        buscarUsuarioPorEmailTeste();
    }

    public static void buscarUsuarioPorEmailTeste() {
        try {
            // Instanciar os DAOs
            UsuarioDao usuarioDao = DaoFactory.getUsuarioDao();
            ContaDao contaDao = DaoFactory.getContaDao();
            RecebimentoDao recebimentoDao = DaoFactory.getRecebimentoDao();

            // Definir um email para buscar no banco
            String email = "joao.silva@email.com";

            // Buscar o usuário pelo email
            Usuario usuario = usuarioDao.buscarPorEmail(email);

            // Verificar se o usuário foi encontrado
            if (usuario != null) {
                System.out.println("Usuário encontrado:");
                System.out.println("ID: " + usuario.getId());
                System.out.println("Nome: " + usuario.getNome());
                System.out.println("Email: " + usuario.getEmail());

                // Buscar a conta associada ao usuário
                Conta conta = contaDao.buscarPorIdUsuario(usuario.getId());

                // Verificar se a conta foi encontrada
                if (conta != null) {
                    System.out.println("Conta associada ao usuário:");
                    System.out.println("ID da Conta: " + conta.getIdConta());
                    System.out.println("Banco: " + conta.getBanco());
                    System.out.println("Tipo de Conta: " + conta.getTipoConta());

                    // Listar os recebimentos dessa conta
                    List<Recebimento> recebimentos = recebimentoDao.listarRecebimentosPorConta(conta.getIdConta());

                    if (!recebimentos.isEmpty()) {
                        System.out.println("Recebimentos associados à conta:");
                        for (Recebimento recebimento : recebimentos) {
                            System.out.println("ID: " + recebimento.getIdRecebimento());
                            System.out.println("Descrição: " + recebimento.getDescricao());
                            System.out.println("Valor: " + recebimento.getValorRecebimento());
                            System.out.println("Data: " + recebimento.getDataRecebimento());
                        }
                    } else {
                        System.out.println("Nenhum recebimento encontrado para esta conta.");
                    }
                } else {
                    System.out.println("Nenhuma conta associada ao usuário.");
                }
            } else {
                System.out.println("Usuário não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
