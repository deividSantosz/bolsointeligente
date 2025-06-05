package com.example.bolsointeligente.config;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;
public class Config {
    public static String loadProperties(Context context) {
        Properties properties = new Properties();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
            String token = properties.getProperty("token");
            Log.d("Config", "Token carregado: " + token);
            return token; // Verificação
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
