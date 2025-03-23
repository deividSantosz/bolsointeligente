package com.example.bolsointeligente.activities.Acoes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsointeligente.R;

public class ViewHolderAcao extends RecyclerView.ViewHolder {
    TextView tv_codigo_acao, tv_nome_empresa, tv_preco, tv_variacao;
    ImageView img_acao;

    public ViewHolderAcao(@NonNull View itemView) {
        super(itemView);
        tv_codigo_acao = itemView.findViewById(R.id.tv_codigo_acao);
        tv_nome_empresa = itemView.findViewById(R.id.tv_nome_empresa);
        tv_preco = itemView.findViewById(R.id.tv_preco);
        tv_variacao = itemView.findViewById(R.id.tv_variacao);
        img_acao = itemView.findViewById(R.id.img_acao);
    }
}
