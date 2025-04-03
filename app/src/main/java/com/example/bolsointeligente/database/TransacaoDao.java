package com.example.bolsointeligente.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransacaoDao {
    @Insert
    void inserirTransacao(Transacao transacao);

    @Query("SELECT * FROM transacao WHERE id = :id")
    Transacao getTransacaoById(int id);

    @Update
    void atualizarTransacao(Transacao transacao);

    @Query("SELECT * FROM transacao WHERE idUsuario = :usuarioId")
    List<Transacao> listarTransacoesPorUsuario(int usuarioId);

    @Query("SELECT SUM(valor) FROM transacao WHERE idUsuario = :usuarioId")
    Double getTotalTransacoesPorUsuario(int usuarioId);

    @Query("DELETE FROM transacao WHERE id = :transacaoId")
    void deletarTransacao(int transacaoId);

}
