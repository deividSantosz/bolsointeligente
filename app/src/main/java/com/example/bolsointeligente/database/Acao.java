package com.example.bolsointeligente.database;

public class Acao {
    private String codigo;
    private String nome;
    private String preco;
    private String variacao;

    public Acao(String codigo, String nome, String preco, String variacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.variacao = variacao;
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getPreco() { return preco; }
    public String getVariacao() { return variacao; }
}
