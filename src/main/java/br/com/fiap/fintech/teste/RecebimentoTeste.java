package br.com.fiap.fintech.teste;

import br.com.fiap.fintech.dao.impl.OracleRecebimentoDao;
import br.com.fiap.fintech.exception.DBException;
import br.com.fiap.fintech.model.Recebimento;

import java.util.Date;
import java.util.List;

public class RecebimentoTeste {

    public static void main(String[] args) {
//        // Criar uma instância da DAO
        OracleRecebimentoDao recebimentoDao = new OracleRecebimentoDao();
//
//        // Criar um novo recebimento para a conta com ID 4
//        Recebimento novoRecebimento = new Recebimento();
//        novoRecebimento.setDescricao("Salário");
//        novoRecebimento.setValorRecebimento(3000.00);
//        novoRecebimento.setDataRecebimento(new Date()); // Data de hoje como exemplo
//        novoRecebimento.setIdConta(4); // ID da conta ao qual o recebimento será vinculado
//
//        try {
//            // Cadastrar o novo recebimento
//            recebimentoDao.cadastrar(novoRecebimento);
//            System.out.println("Recebimento cadastrado com sucesso!");
//
//        } catch (DBException e) {
//            e.printStackTrace();
//            System.out.println("Erro ao cadastrar recebimento.");
//        }

        // Listar todos os recebimentos cadastrados
        try {
            List<Recebimento> listaRecebimentos = recebimentoDao.listar();
            System.out.println("Lista de Recebimentos Cadastrados:");
            for (Recebimento recebimento : listaRecebimentos) {
                System.out.println(recebimento);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao listar recebimentos.");
        }
    }
}
