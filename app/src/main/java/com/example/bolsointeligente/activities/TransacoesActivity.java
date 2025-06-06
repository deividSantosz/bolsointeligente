package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.listaTransacoes.ListaTransacoesAdapter;
import com.example.bolsointeligente.database.Database;
import com.example.bolsointeligente.database.Transacao;
import com.example.bolsointeligente.database.TransacaoDao;
import com.example.bolsointeligente.singleton.UsuarioSingleton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class TransacoesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListaTransacoesAdapter adapter;
    TextView total_transacoes;
    Database db;
    private int usuarioId;
    private TransacaoDao transacaoDao;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacoes);
        db = Room.databaseBuilder(getApplicationContext(), com.example.bolsointeligente.database.Database.class, "Bolso Inteligente DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        transacaoDao = db.transacaoDao();
        recyclerView = findViewById(R.id.rv_transacoes);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        total_transacoes = findViewById(R.id.txt_total_transacoes);
        usuarioId = (int) UsuarioSingleton.getInstance().getUserId();
        carregarTotalTransacoes();
        adapter = new ListaTransacoesAdapter(new ArrayList<>(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        carregarTransacoes();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Transacao transacao = adapter.listaTransacoes.get(position);
                int transacaoId = (int) transacao.getId();
                if (direction == ItemTouchHelper.LEFT) {
                    transacaoDao.deletarTransacao(transacaoId); adapter.listaTransacoes.remove(position);
                    adapter.notifyItemRemoved(position);
                    carregarTotalTransacoes();
                } else if (direction == ItemTouchHelper.RIGHT) {
                    abrirTelaEdicao(transacaoId);
                }

            }
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Paint paint = new Paint();
                float cornerRadius = 50f;  // Arredondamento das bordas

                float itemViewTop = viewHolder.itemView.getTop();
                float itemViewBottom = viewHolder.itemView.getBottom();
                float itemViewLeft = viewHolder.itemView.getLeft();
                float itemViewRight = viewHolder.itemView.getRight();

                RectF background;
                int iconRes;
                int iconSize = 100;
                int iconMargin = 50;

                if (dX > 0) {  // Swipe para a direita (Editar)
                    int corVerde = ContextCompat.getColor(getApplicationContext(), R.color.primaria);
                    paint.setColor(corVerde);
                    background = new RectF(itemViewLeft, itemViewTop, itemViewLeft + dX, itemViewBottom);
                } else {  // Swipe para a esquerda (Excluir)
                    paint.setColor(Color.RED);
                    background = new RectF(itemViewRight + dX, itemViewTop, itemViewRight, itemViewBottom);

                }
                // Desenhar o fundo arredondado
                c.drawRoundRect(background, cornerRadius, cornerRadius, paint);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


        }).attachToRecyclerView(recyclerView);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home) {
                    Intent intent = new Intent(TransacoesActivity.this, MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.estatisticas) {
                    Intent intent = new Intent(TransacoesActivity.this, EstatisticasActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.simulador) {
                    Intent intent = new Intent(TransacoesActivity.this, SimulacaoActivity.class);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.perfil) {
                    Intent intent = new Intent(TransacoesActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
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
    private void carregarTotalTransacoes() {
        Double total = transacaoDao.getTotalTransacoesPorUsuario(usuarioId);

        if (total == null) {
            total = 0.0;
        }
        total_transacoes.setText(String.format("R$ %.2f", total));
    }


    public void abrirTelaEdicao(int transacaoId) {
        Intent intent = new Intent(this, AlterarTransacaoActivity.class);
        intent.putExtra("transacaoId", transacaoId);
        startActivity(intent);
    }



}