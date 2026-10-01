package com.kapitelweise.kapitelweiseBackend.service;


import java.nio.charset.StandardCharsets;
import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// hier generieren wir den Token, der dann an den User geschickt wird. Wir können da unterschiedliche Sachen mitgeben, hab jetzt einfach mal das


@Service
public class JwtService {

    private static final String SECRET_KEY = "mysecretkeymysecretkeymyxwsecretkeymysecretkey"; 
    // muss so lange sein, sonst ist es bei mir auf Fehler gelaufen, später vllt. einen neuen erzeugen lassen
    //bzw. eigentlich Umgebungsvariable, aber für Testzwecke erstmal so
    
public String generateToken(String username){
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new java.util.Date())
        .setExpiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .addClaims(new java.util.HashMap<>())
        .signWith(getSignedKey(), SignatureAlgorithm.HS256)
        .compact();


}

    private Key getSignedKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }


/*
prüft den Token ob er valide ist wenn er mitgeschickt wird.
Dabei wird ein Parser gebaut, der den Token zerlegt. in setSigning geben wir ihm den selben geheimen Key mit, build baut den Parser
dann: 1. in drei Teile zerlegt, Signatur wird nachgerechnet, Ablaufdatum wird geprüft
getBody gibt den Body also die Claims zurück, die wir oben definiert haben.
 */
    public Claims verifyTokenAndExtract(String token){
        return Jwts.parserBuilder()
        .setSigningKey(getSignedKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
    }

    //hier werden die Claims rausgeholt
    public String extractUsername(String token) {
        Claims claims = verifyTokenAndExtract(token);
        return claims.getSubject();
    }

    public String getExpirationDate(String token) {
        Claims claims = verifyTokenAndExtract(token);
        return claims.getExpiration().toString();
    }

    public boolean isTokenExpired(String token) {
        Claims claims = verifyTokenAndExtract(token);
        return claims.getExpiration().before(new java.util.Date());
    }

}