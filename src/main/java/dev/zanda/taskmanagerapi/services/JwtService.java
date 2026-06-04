package dev.zanda.taskmanagerapi.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    //Inietto la chiave segreta da application.properites
    @Value("${jwt.secret.key}")
    private String secretKey;

    //Metodo helper per ottenere la chiave di firma
    private Key getSignKey(){
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Usa System.currentTimeMillis()
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // Token valido per 30 minuti
                .signWith(getSignKey(), SignatureAlgorithm.HS256) // Usa getSignKey()
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
