package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bolsointeligente.database.Database;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.listaTransacoes.ListaTransacoesAdapter;
import com.example.bolsointeligente.database.Transacao;
import com.example.bolsointeligente.database.TransacaoDao;
import com.example.bolsointeligente.fragments.AdicionarTransacao;
import com.example.bolsointeligente.singleton.UsuarioSingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    Button btnAddTransacao;
    RecyclerView rv_lista_transacoes;
    TextView txt_ver_todos, txtNome;
    ListaTransacoesAdapter adapter;
    Database db;
    private int usuarioId;
    private TransacaoDao transacaoDao;

     BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        db = Room.databaseBuilder(getApplicationContext(), Database.class, "Bolso Inteligente DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        btnAddTransacao = findViewById(R.id.btn_add_transacao);
        rv_lista_transacoes = findViewById(R.id.rv_transacoes_menu);
        txt_ver_todos = findViewById(R.id.txt_ver_todos);
        txtNome = findViewById(R.id.textNomeMenu);

        usuarioId = (int) UsuarioSingleton.getInstance().getUserId();
        txtNome.setText(UsuarioSingleton.getInstance().getuserNome());
        transacaoDao = db.transacaoDao();
        adapter = new ListaTransacoesAdapter(new ArrayList<>(), this);
        rv_lista_transacoes.setLayoutManager(new LinearLayoutManager(this));
        rv_lista_transacoes.setAdapter(adapter);

        // Agora chama o método para carregar as transações
        carregarTransacoes();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.perfil) {
                    Intent intent = new Intent(MenuActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (item.getItemId() == R.id.Transacoes) {
                    Intent intent = new Intent(MenuActivity.this, TransacoesActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (item.getItemId() == R.id.estatisticas) {
                    Intent intent = new Intent(MenuActivity.this, EstatisticasActivity.class);
                    startActivity(intent);
                    return true;
                }

                if (item.getItemId() == R.id.simulador) {
                    Intent intent = new Intent(MenuActivity.this, SimulacaoActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });
       btnAddTransacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Criando uma instância do fragmento
                AdicionarTransacao fragment = new AdicionarTransacao();

                // Iniciando a transação para substituir o conteúdo do FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, fragment) // Aqui estamos substituindo o fragmento atual pelo novo
                        .addToBackStack(null) // Para permitir que o usuário volte para a tela anterior
                        .commit();
            }
        });

       txt_ver_todos.setOnClickListener((View view) -> {
           Intent intent = new Intent(MenuActivity.this, TransacoesActivity.class);
           startActivity(intent);
       });
}

private void carregarTransacoes() {
        List<Transacao> transacoes = transacaoDao.listarTransacoesPorUsuario(usuarioId);
        if (transacoes != null && !transacoes.isEmpty()) {
            adapter.listaTransacoes.clear();
            adapter.listaTransacoes.addAll(transacoes);
            adapter.notifyDataSetChanged();
        }
    }
}