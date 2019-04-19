package com.example.springframework.oshop.security;

public class SecurityConstants {
    static final String SIGN_UP_URL = "/api/users/register";
    static final String LOGIN_URL = "/api/users/login";
    static final String CATEGORIES_URL = "/api/admin/oshop/categories";
    static final String GUEST_URLS = "/api/user/oshop/**/**/**";
    static final String H2_URL = "/h2-console/**/**";
    static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final long EXPIRATION_TIME = 3000_000; // Few minutes
}
