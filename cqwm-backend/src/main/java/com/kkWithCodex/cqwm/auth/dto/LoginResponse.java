package com.kkWithCodex.cqwm.auth.dto;

public class LoginResponse {

    private String token;
    private long expiresIn;
    private AdminProfile profile;

    public LoginResponse() {
    }

    public LoginResponse(String token, long expiresIn, AdminProfile profile) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.profile = profile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public AdminProfile getProfile() {
        return profile;
    }

    public void setProfile(AdminProfile profile) {
        this.profile = profile;
    }
}
