package com.example.bolsointeligente.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UsuarioTransacao {
    @Embedded
    public Usuario usuario;

    @Relation(
            parentColumn = "id",
            entityColumn = "idUsuario"
    )
    public List<Transacao> transacoes;
}
