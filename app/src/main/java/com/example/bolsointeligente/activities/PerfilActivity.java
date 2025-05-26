package com.example.bolsointeligente.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.bolsointeligente.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PerfilActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    TextView txt_sair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil2);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        txt_sair = findViewById(R.id.txt_sair);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()  {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.perfil) {
                    return true;
                }
                if (itemId == R.id.home) {
                    Intent intent = new Intent(PerfilActivity.this, MenuActivity.class);
                    startActivity(intent);
                    return true;
                }
              else if (item.getItemId() == R.id.estatisticas) {
                    Intent intent = new Intent(PerfilActivity.this, EstatisticasActivity.class);
                    startActivity(intent);
                    return true;
                }
              else if (item.getItemId() == R.id.simulador) {
                    Intent intent = new Intent(PerfilActivity.this, SimulacaoActivity.class);
                    startActivity(intent);
                    return true;
                }

                return false;
            }

        });

        bottomNavigationView.setSelectedItemId(R.id.perfil);
        txt_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}