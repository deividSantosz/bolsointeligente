package com.example.bolsointeligente.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransacaoDao {
    @Insert
    void inserirTransacao(Transacao transacao);

    @Query("SELECT * FROM transacao WHERE idUsuario = :usuarioId")
    List<Transacao> listarTransacoesPorUsuario(int usuarioId);
}
