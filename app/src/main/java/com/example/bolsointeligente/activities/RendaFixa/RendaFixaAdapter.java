package com.example.bolsointeligente.activities.RendaFixa;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsointeligente.R;
import com.example.bolsointeligente.activities.Acoes.ViewHolderAcao;
import com.example.bolsointeligente.database.Acao;
import com.example.bolsointeligente.database.RendaFixa;

import java.util.List;

public class RendaFixaAdapter extends RecyclerView.Adapter<ViewHolderRendaFixa>{

    private List<RendaFixa> rendaFixaList;

    public RendaFixaAdapter(List<RendaFixa> rendaFixaList) {
        this.rendaFixaList = rendaFixaList;
    }

    @NonNull
    @Override
    public ViewHolderRendaFixa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewHolderRendaFixa viewHolder = new ViewHolderRendaFixa(inflater.inflate(R.layout.recycler_view_item_rendafixa, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRendaFixa holder, int position) {
        RendaFixa rendaFixa = rendaFixaList.get(position);
        holder.tvNomeInvestimento.setText(rendaFixa.getNome());
        holder.tvVencimento.setText(rendaFixa.getVencimento());
        holder.tvRentabilidade.setText(rendaFixa.getRendimento() + "% a.a.");
        holder.tvDetalheRentabilidade.setText(rendaFixa.getDetalheRentabilidade());
    }

    @Override
    public int getItemCount() {
        return rendaFixaList.size();
    }
}
