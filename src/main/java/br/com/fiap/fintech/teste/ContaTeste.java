package br.com.fiap.fintech.teste;

import br.com.fiap.fintech.dao.impl.OracleContaDao;
import br.com.fiap.fintech.model.Conta;
import br.com.fiap.fintech.exception.DBException;

import java.util.List;

public class ContaTeste {

    public static void main(String[] args) {
        // Criar instância da DAO
        OracleContaDao contaDao = new OracleContaDao();

        // Criar uma nova conta para o usuário com ID 1
        Conta novaConta = new Conta();
        novaConta.setBanco("Banco do Brasil");
        novaConta.setTipoConta("Corrente");
        novaConta.setIdUsuario(1); // Definindo o ID do usuário como 1

        try {
            // Cadastrar a nova conta
            contaDao.cadastrar(novaConta);
            System.out.println("Conta cadastrada com sucesso!");

        } catch (DBException e) {
            e.printStackTrace();
            System.out.println("Erro ao cadastrar conta.");
        }

        // Listar todas as contas cadastradas
        try {
            List<Conta> listaContas = contaDao.listar();
            System.out.println("Lista de Contas Cadastradas:");
            for (Conta conta : listaContas) {
                System.out.println(conta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao listar contas.");
        }
    }
}
