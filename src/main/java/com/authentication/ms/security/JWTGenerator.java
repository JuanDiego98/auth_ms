package com.authentication.ms.security;

import com.authentication.ms.models.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.authentication.ms.repository.UserRepository;

import java.util.Date;

@Component
public class JWTGenerator {

    private UserRepository userRepository;

    @Autowired
    public JWTGenerator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int getUserIdByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("Usuario '" + username + "' no encontrado");
        }
        return userEntity.getUserId();
    }

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        int userId = getUserIdByUsername(username);
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        String token = Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(expireDate)
            .claim("userId", userId)
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET)
            .compact();

        return token;
    }

    public String getUserNameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception ex){
            throw new AuthenticationCredentialsNotFoundException("JWT caducado o incorrecto");
        }
    }
}
