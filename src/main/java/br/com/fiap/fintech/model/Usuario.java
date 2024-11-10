package br.com.fiap.fintech.model;

import br.com.fiap.fintech.util.CriptografiaUtils;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;

    // Construtor com parâmetros
    public Usuario(int id, String nome, String email, String senha) {
        super();
        this.id = id;
        this.nome = nome;
        this.email = email;
        setSenha(senha);  // Usa o método setSenha para criptografar
    }

    // Construtor com email e senha
    public Usuario(String email, String senha) {
        super();
        this.email = email;
        setSenha(senha);  // Usa o método setSenha para criptografar
    }

    // Construtor padrão
    public Usuario() {
        super();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        try {
            this.senha = CriptografiaUtils.criptografar(senha);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
