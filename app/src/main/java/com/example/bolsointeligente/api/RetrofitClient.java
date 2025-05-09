package com.example.bolsointeligente.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static AcaoApiService getApiService() {
        if (retrofit == null) {
            // Cria o Gson com a configuração leniente
            Gson gson = new GsonBuilder()
                    .setLenient() // Define que o Gson deve ser leniente
                    .create();
            String baseUrl = "https://brapi.dev/api/";
           // String baseUrl = ("https://brapi.dev/api/quote/" + ticket +"?&token=" + token);
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl) // Base URL sem token ou ticket
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Usa o Gson configurado
                    .build();
        }
        return retrofit.create(AcaoApiService.class);
    }
}
