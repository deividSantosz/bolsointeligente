package com.example.bolsointeligente.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bolsointeligente.R;

public class DiversificacaoCarteira extends AppCompatActivity {

    ImageView setadiversificacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diversificacao_carteira);
        setadiversificacao = findViewById(R.id.img_diversificacao);
        setadiversificacao.setOnClickListener((
                View view) -> {
            finish();
        });
    }

}