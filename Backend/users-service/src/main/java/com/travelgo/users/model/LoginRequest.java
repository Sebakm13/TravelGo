package com.travelgo.users.model;

public class LoginRequest {
    private String username;
    private String password;
    private Integer expiresInMins;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getExpiresInMins() {
        return expiresInMins;
    }

    public void setExpiresInMins(Integer expiresInMins) {
        this.expiresInMins = expiresInMins;
    }
}
