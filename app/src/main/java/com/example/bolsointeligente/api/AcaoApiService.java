package com.example.bolsointeligente.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AcaoApiService {
    // Usando o token como header e o símbolo da ação como parâmetro na URL
    @GET("quote/{ticket}")
    Call<CotacaoResponse> getCotacao(
            @Path("ticket") String ticket,
            @Query("token") String token
    );

}
