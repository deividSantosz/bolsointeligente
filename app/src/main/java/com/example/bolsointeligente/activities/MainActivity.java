package com.example.bolsointeligente.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.database.Database;
import com.example.bolsointeligente.database.Usuario;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnEntrar,btnCadastrar;
    EditText edit_email, edit_senha;
    Database db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         db = Room.databaseBuilder(getApplicationContext(), Database.class, "Bolso Inteligente DB")
                 .fallbackToDestructiveMigration()
                 .allowMainThreadQueries()
                 .build();

         insereDadosUsuario();
         btnEntrar = findViewById(R.id.btnEntrar);
         btnCadastrar = findViewById(R.id.btnCadastrar);
         edit_email = findViewById(R.id.edit_Email);
         edit_senha = findViewById(R.id.edit_senha_cadastro);

         btnCadastrar.setOnClickListener((View view) -> {
             Intent intent = new Intent(this, CadastroActivity.class);
             startActivity(intent);
        });
         btnEntrar.setOnClickListener((View view) -> {
            logar();
         });

    }

    private void logar() {
        if(validarCampos() == false) {
            return;
        }
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();
        if (validarLogin(email, senha) == false) {
            Toast.makeText(this,"Dados de login incorretos",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private boolean validarCampos(){
        if(edit_email.getText().toString().trim().equals("")){
            edit_email.setError("Email obrigatório!");
            return false;
        }
        if(edit_senha.getText().toString().trim().equals("")){
            edit_senha.setError("Senha obrigatória!");
            return false;
        }
        return true;
    }

    private boolean validarLogin(String email, String senha){
        Usuario usuario = db.usuarioDao().getUserLogin(email,senha);
        if (usuario!=null) {
            return true;
        }
        else
            return false;
    }

    private void insereDadosUsuario(){
        List<Usuario> listaUsuarios = db.usuarioDao().getAll();
        if (!listaUsuarios.isEmpty()) {
            return;
        }

        Usuario master = new Usuario();
        master.nome =  "master";
        master.email = "master123@gmail.com";
        master.telefone = "73998513975";
        master.senha = "master123";
        db.usuarioDao().insereUsuario(master);

        Usuario usuario = new Usuario();
        usuario.nome = "teste";
        usuario.email = "teste@gmail.com";
        usuario.telefone = "73998432784";
        usuario.senha = "teste123";

        db.usuarioDao().insereUsuario(usuario); // Inserir o usuário no banco

    }
}
