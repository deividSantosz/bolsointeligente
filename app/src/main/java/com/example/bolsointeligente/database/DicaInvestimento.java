package com.example.bolsointeligente.database;

public class DicaInvestimento {
    private String titulo;
    private String descricao;

    public DicaInvestimento(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }
}

