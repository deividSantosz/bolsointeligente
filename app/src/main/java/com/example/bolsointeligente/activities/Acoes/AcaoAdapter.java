package com.example.bolsointeligente.activities.Acoes;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.database.Acao;

import java.util.List;


public class AcaoAdapter extends RecyclerView.Adapter<ViewHolderAcao>{
    private List<Acao> listaAcoes;
    public AcaoAdapter(List<Acao> listaAcoes) {
        this.listaAcoes = listaAcoes;
    }


    @NonNull
    @Override
    public ViewHolderAcao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewHolderAcao viewHolder = new ViewHolderAcao(inflater.inflate(R.layout.recycler_view_item_acao, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAcao holder, int position) {

        Acao acao = listaAcoes.get(position);
        holder.tv_codigo_acao.setText(acao.getCodigo());
        holder.tv_nome_empresa.setText(acao.getNome());
        holder.tv_preco.setText("R$ " + acao.getPreco());
        holder.tv_variacao.setText(acao.getVariacao());

        // Define a cor da variação (+ verde, - vermelho)
        if (acao.getVariacao().contains("-")) {
            holder.tv_variacao.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.vermelho));
            holder.img_acao.setImageResource(R.drawable.perda);
        } else {
            holder.tv_variacao.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.primaria));
            holder.img_acao.setImageResource(R.drawable.ganho);

        }

    }

    @Override
    public int getItemCount() {
        return listaAcoes.size();
    }
}
