package com.example.bolsointeligente.database;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Usuario.class}, version = 1, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract UsuarioDao usuarioDao();
}
