package com.example.bolsointeligente.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.example.bolsointeligente.config.Config;

public interface AcaoApiService {
    @GET("quote/{ticket}")
    Call<CotacaoResponse> getCotacao(
            @Path("ticket") String ticket,
            @Query("token") String token
    );

}
