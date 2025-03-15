package com.example.bolsointeligente.singleton;

public class UsuarioSingleton {

    private static UsuarioSingleton instance;
    private long userId;
    private String userNome;

    public static synchronized UsuarioSingleton getInstance() {
        if (instance == null) {
            instance = new UsuarioSingleton();
        }
        return instance;
    }
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getuserNome() {
        return userNome;
    }

    public void setuserNome(String userNome) {
        this.userNome = userNome;
    }
}
