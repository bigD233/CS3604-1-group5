package com.example.whitecommunity.result;

public class LoginResult {
    private int userId;
    private String token;

    public LoginResult() {
    }

    public LoginResult(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getToken() {
        return this.token;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
