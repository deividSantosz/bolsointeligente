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

public class RendaFixaVariavel extends AppCompatActivity {
    ImageView seta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renda_fixa_variavel);
        seta = findViewById(R.id.img_seta);

        seta.setOnClickListener((View view) -> {
            finish();
        });

    }
}