package com.example.springframework.oshop.payload;


import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTLoginSuccessResponse {

    private boolean success;
    private Collection<? extends GrantedAuthority> authorities;
    private String token;

    public JWTLoginSuccessResponse(boolean success, Collection<? extends GrantedAuthority> authorities, String token) {
        this.success = success;
        this.authorities = authorities;
        this.token = token;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
