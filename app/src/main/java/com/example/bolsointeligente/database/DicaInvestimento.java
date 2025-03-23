package com.example.bolsointeligente.database;

public class DicaInvestimento {
    private String titulo;
    private String descricao;

    private Class<?> activityDestino;

    public DicaInvestimento(String titulo, String descricao, Class<?> activityDestino ) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.activityDestino = activityDestino;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Class<?> getActivityDestino() {
        return activityDestino;
    }

    public void setActivityDestino(Class<?> activityDestino) {
        this.activityDestino = activityDestino;
    }
}

