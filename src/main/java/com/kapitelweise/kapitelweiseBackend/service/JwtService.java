package com.kapitelweise.kapitelweiseBackend.service;


import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// hier generieren wir den Token, der dann an den User geschickt wird. Wir können da unterschiedliche Sachen mitgeben, hab jetzt einfach mal das


@Service
public class JwtService {

    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkeymysecretkey"; 
    // muss so lange sein, sonst ist es bei mir auf Fehler gelaufen, später vllt. einen neuen erzeugen lassen
    //bzw. eigentlich Umgebungsvariable, aber für Testzwecke erstmal so
    
public String generateToken(String username){
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new java.util.Date())
        .setExpiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        .addClaims(new java.util.HashMap<>())
        .signWith(SignatureAlgorithm.HS256, getSignedKey())
        .compact();


}

private Key getSignedKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
}

}