package br.com.fiap.fintech.model;

import java.util.Date;

public class Despesa {
    private int idDespesa;
    private String descricao;
    private double valorDespesa;
    private Date dataDespesa;
    private int idConta;

    // Construtores
    public Despesa() {}

    public Despesa(int idDespesa, String descricao, double valorDespesa, Date dataDespesa, int idConta) {
        this.idDespesa = idDespesa;
        this.descricao = descricao;
        this.valorDespesa = valorDespesa;
        this.dataDespesa = dataDespesa;
        this.idConta = idConta;
    }

    // Getters e Setters
    public int getIdDespesa() {
        return idDespesa;
    }

    public void setIdDespesa(int idDespesa) {
        this.idDespesa = idDespesa;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorDespesa() {
        return valorDespesa;
    }

    public void setValorDespesa(double valorDespesa) {
        this.valorDespesa = valorDespesa;
    }

    public Date getDataDespesa() {
        return dataDespesa;
    }

    public void setDataDespesa(Date dataDespesa) {
        this.dataDespesa = dataDespesa;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    @Override
    public String toString() {
        return "Despesa{" +
                "idDespesa=" + idDespesa +
                ", descricao='" + descricao + '\'' +
                ", valorDespesa=" + valorDespesa +
                ", dataDespesa=" + dataDespesa +
                ", idConta=" + idConta +
                '}';
    }
}
