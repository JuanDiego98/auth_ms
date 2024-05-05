package com.authentication.ms.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 7200000;
    public static final String JWT_SECRET = "G6qmQ3F1EIjaoafKpnw6wFvaK69MzoZVVIhk4Ex5qqSRO7fVAxnzpXW7FOi9tRIKhqFunQyMZjeuZRFxbJegGg==";
}