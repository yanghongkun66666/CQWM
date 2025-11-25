package com.kkWithCodex.cqwm.auth.dto;

public class AdminProfile {

    private Long id;
    private String username;
    private String fullName;
    private String phone;
    private String email;

    public AdminProfile() {
    }

    public AdminProfile(Long id, String username, String fullName, String phone, String email) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
