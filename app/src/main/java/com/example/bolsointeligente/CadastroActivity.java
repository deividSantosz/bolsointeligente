package com.example.bolsointeligente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bolsointeligente.database.Database;
import com.example.bolsointeligente.database.Usuario;

public class CadastroActivity extends AppCompatActivity {

    EditText editNome, editEmail, editSenha, editTelefone;
    Button btnCadastrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editTelefone = findViewById(R.id.editTelefone);
        editSenha = findViewById(R.id.editSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);


        btnCadastrar.setOnClickListener((View view ) -> {
            cadastro();
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        });
    }

    public void cadastro() {
        if(validarCampos() == false) {
            return;
        }
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String telefone = editTelefone.getText().toString();
        String senha = editSenha.getText().toString();
        if (validarCadastro(nome, email, telefone, senha) == true) {
            CadastrarUsuario(nome, email, telefone, senha);
        }

    }

    private boolean validarCampos(){

        if(editNome.getText().toString().trim().equals("")){
            editNome.setError("Nome obrigatório!");
            return false;
        }
        if(editEmail.getText().toString().trim().equals("")){
            editEmail.setError("Email obrigatório!");
            return false;
        }
        if(editTelefone.getText().toString().trim().equals("")){
            editTelefone.setError("Telefone obrigatório!");
            return false;
        }
        if(editSenha.getText().toString().trim().equals("")){
            editSenha.setError("Senha obrigatória!");
            return false;
        }
        return true;
    }

    private boolean validarCadastro(String nome, String email, String telefone, String senha) {
        Database db = Room.databaseBuilder(getApplicationContext(),Database.class,"Bolso inteligente BD")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        Usuario usuario = db.usuarioDao().getUserCadastro(email);
        if (usuario!=null) {
            return false;
        }
        return true;
    }

    private void CadastrarUsuario(String nome, String email, String telefone, String senha) {
        Database db = Room.databaseBuilder(getApplicationContext(),Database.class,"Bolso inteligente BD")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        Usuario usuario = new Usuario();

        usuario.nome = nome;
        usuario.email = email;
        usuario.telefone = telefone;
        usuario.senha = senha;

        db.usuarioDao().insereUsuario(usuario);
    }
}