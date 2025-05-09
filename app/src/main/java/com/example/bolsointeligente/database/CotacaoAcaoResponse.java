// CotacaoAcaoResponse.java
package com.example.bolsointeligente.database;

import com.google.gson.annotations.SerializedName;

public class CotacaoAcaoResponse {

    @SerializedName("symbol")
    private String symbol;

    @SerializedName("shortName")
    private String nome;

    @SerializedName("regularMarketPrice")
    private double regularMarketPrice;

    @SerializedName("regularMarketChangePercent")
    private double changePercent;

    public String getSymbol() {
        return symbol;
    }

    public String getNome() {
        return nome;
    }

    public double getRegularMarketPrice() {
        return regularMarketPrice;
    }

    public double getChangePercent() {
        return changePercent;
    }
}
