package com.example.bolsointeligente.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.example.bolsointeligente.R;
import com.example.bolsointeligente.fragments.AdicionarTransacao;

public class AlterarTransacaoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_transacao);

        AdicionarTransacao fragment = new AdicionarTransacao();
        fragment.setArguments(getIntent().getExtras());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmento2, fragment);
        transaction.commit();
    }
}
