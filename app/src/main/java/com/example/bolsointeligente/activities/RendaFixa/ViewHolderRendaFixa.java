package com.example.bolsointeligente.activities.RendaFixa;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bolsointeligente.R;

public class ViewHolderRendaFixa extends RecyclerView.ViewHolder {

    TextView tvNomeInvestimento, tvVencimento, tvRentabilidade, tvDetalheRentabilidade;
    public ViewHolderRendaFixa(@NonNull View itemView) {
        super(itemView);

        tvNomeInvestimento = itemView.findViewById(R.id.tvNomeInvestimento);
        tvVencimento = itemView.findViewById(R.id.tvVencimento);
        tvRentabilidade = itemView.findViewById(R.id.tvRentabilidade);
        tvDetalheRentabilidade = itemView.findViewById(R.id.tvDetalheRentabilidade);
    }
}
