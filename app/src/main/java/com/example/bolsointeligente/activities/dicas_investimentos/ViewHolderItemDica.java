package com.example.bolsointeligente.activities.dicas_investimentos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsointeligente.R;

public class ViewHolderItemDica  extends RecyclerView.ViewHolder{
    TextView tituloDica, descricaoDica, saibaMais;
    public ViewHolderItemDica(@NonNull View itemView) {
        super(itemView);
        tituloDica = itemView.findViewById(R.id.tituloDica);
        descricaoDica = itemView.findViewById(R.id.descricaoDica);
        saibaMais = itemView.findViewById(R.id.saibaMais);
    }
}
