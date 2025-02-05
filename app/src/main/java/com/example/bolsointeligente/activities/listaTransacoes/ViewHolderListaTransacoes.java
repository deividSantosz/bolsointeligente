package com.example.bolsointeligente.activities.listaTransacoes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsointeligente.R;

public class ViewHolderListaTransacoes extends RecyclerView.ViewHolder {
    TextView txt_categoria, txt_data, txt_descricao, txt_valor;
    public ViewHolderListaTransacoes(@NonNull View itemView) {
        super(itemView);
        txt_categoria = itemView.findViewById(R.id.txt_categoria);
        txt_data = itemView.findViewById(R.id.txt_data);
        txt_descricao = itemView.findViewById(R.id.txt_descricao);
        txt_valor = itemView.findViewById(R.id.txt_valor);
    }
}
