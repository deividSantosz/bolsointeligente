package com.example.bolsointeligente.database;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM usuario")
    public List<Usuario> getAll();

    @Query("SELECT * FROM usuario WHERE id=:id")
    public Usuario getUserById(long id);
    @Query("SELECT * FROM usuario WHERE email=:email AND senha=:senha")
    public Usuario getUserLogin(String email, String senha);

    @Query("SELECT * FROM usuario WHERE email=:email")
    public Usuario getUserCadastro( String email);

    @Insert
    public long insereUsuario(Usuario usuario);

    @Transaction
    @Query("SELECT * FROM usuario WHERE id = :userId")
    UsuarioTransacao getTransacoesByUserId(long userId);


}
