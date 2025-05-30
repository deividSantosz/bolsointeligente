package com.example.bolsointeligente.database;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Usuario.class, Transacao.class}, version = 8, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();
    public abstract TransacaoDao transacaoDao();
}
