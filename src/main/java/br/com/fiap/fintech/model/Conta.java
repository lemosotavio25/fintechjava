package br.com.fiap.fintech.model;

public class Conta {
    private int idConta;
    private String banco;
    private String tipoConta;
    private int idUsuario;

    // Construtores
    public Conta() {}

    public Conta(int idConta, String banco, String tipoConta, int idUsuario) {
        this.idConta = idConta;
        this.banco = banco;
        this.tipoConta = tipoConta;
        this.idUsuario = idUsuario;
    }

    // Getters e Setters
    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "idConta=" + idConta +
                ", banco='" + banco + '\'' +
                ", tipoConta='" + tipoConta + '\'' +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
