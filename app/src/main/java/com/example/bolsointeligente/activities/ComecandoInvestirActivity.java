package com.example.bolsointeligente.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bolsointeligente.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ComecandoInvestirActivity extends AppCompatActivity {
    TextView txt_clique;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comecando_investir);
         txt_clique = findViewById(R.id.txt_clique_aqui_investimentos);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.simulador) {
                    return true;
                }
               else if (itemId == R.id.home) {
                    Intent intent = new Intent(ComecandoInvestirActivity.this, MenuActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.estatisticas) {
                    Intent intent = new Intent(ComecandoInvestirActivity.this, EstatisticasActivity.class);
                    startActivity(intent);
                    return true;
                }

                else if (item.getItemId() == R.id.perfil) {
                    Intent intent = new Intent(ComecandoInvestirActivity.this, PerfilActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

        });
        bottomNavigationView.setSelectedItemId(R.id.simulador);


        txt_clique.setOnClickListener((View view ) -> {
            Intent intent = new Intent(ComecandoInvestirActivity.this, InvestimentosActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
