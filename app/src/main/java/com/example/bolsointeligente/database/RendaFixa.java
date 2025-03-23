package com.example.bolsointeligente.database;

public class RendaFixa {
    private String nome;
    private String vencimento;
    private Double rendimento;
    private String tipoRentabilidade;
    private String detalheRentabilidade;


    public RendaFixa(String nome, String vencimento, double rendimento, String tipoRentabilidade, String detalheRentabilidade) {
        this.nome = nome;
        this.vencimento = vencimento;
        this.rendimento = rendimento;
        this.tipoRentabilidade = tipoRentabilidade;
        this.detalheRentabilidade = detalheRentabilidade;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getVencimento() { return vencimento; }
    public void setVencimento(String vencimento) { this.vencimento = vencimento; }

    public double getRendimento() { return rendimento; }
    public void setRendimento(double rendimento) { this.rendimento = rendimento; }

    public String getTipoRentabilidade() { return tipoRentabilidade; }
    public void setTipoRentabilidade(String tipoRentabilidade) { this.tipoRentabilidade = tipoRentabilidade; }

    public String getDetalheRentabilidade() { return detalheRentabilidade; }
    public void setDetalheRentabilidade(String detalheRentabilidade) { this.detalheRentabilidade = detalheRentabilidade; }
}

