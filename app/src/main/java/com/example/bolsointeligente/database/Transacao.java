package com.example.bolsointeligente.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        foreignKeys = @ForeignKey(
                entity = Usuario.class, // Relaciona com a tabela de usuários
                parentColumns = "id", // Coluna na tabela de usuários
                childColumns = "idUsuario", // Coluna nesta tabela
                onDelete = ForeignKey.CASCADE // Exclui as transações ao deletar o usuário
        )
)
public class Transacao {
    @PrimaryKey(autoGenerate = true)
    public long id;
    private double valor;
    private String descricao;
    private String categoria;
    private long  data;
    private int idUsuario; // Chave estrangeira (deve ser igual ao nome em childColumns acima)

    public Transacao() {
    }

    public Transacao(long id, double valor, String descricao, String categoria, long  data, int idUsuario) {
        this.id = id;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.data = data;
        this.idUsuario = idUsuario;
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public long  getData() {
        return data;
    }

    public void setData(long  data) {
        this.data = data;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
