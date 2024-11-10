package br.com.fiap.fintech.model;

import java.util.Date;

public class Recebimento {
    private int idRecebimento;
    private String descricao;
    private double valorRecebimento;
    private Date dataRecebimento;
    private int idConta;

    // Construtores
    public Recebimento() {}

    public Recebimento(int idRecebimento, String descricao, double valorRecebimento, Date dataRecebimento, int idConta) {
        this.idRecebimento = idRecebimento;
        this.descricao = descricao;
        this.valorRecebimento = valorRecebimento;
        this.dataRecebimento = dataRecebimento;
        this.idConta = idConta;
    }

    // Getters e Setters
    public int getIdRecebimento() {
        return idRecebimento;
    }

    public void setIdRecebimento(int idRecebimento) {
        this.idRecebimento = idRecebimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValorRecebimento() {
        return valorRecebimento;
    }

    public void setValorRecebimento(double valorRecebimento) {
        this.valorRecebimento = valorRecebimento;
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    @Override
    public String toString() {
        return "Recebimento{" +
                "idRecebimento=" + idRecebimento +
                ", descricao='" + descricao + '\'' +
                ", valorRecebimento=" + valorRecebimento +
                ", dataRecebimento=" + dataRecebimento +
                ", idConta=" + idConta +
                '}';
    }
}
