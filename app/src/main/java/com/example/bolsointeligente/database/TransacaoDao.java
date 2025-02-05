package com.example.bolsointeligente.database;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface TransacaoDao {
    @Insert
    void inserirTransacao(Transacao transacao);
}
