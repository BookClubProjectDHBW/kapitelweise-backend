package com.kapitelweise.kapitelweiseBackend.service;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException{
    String authorizationHeader = request.getHeader("Authorization");
      String token = null;
      if (request.getCookies() != null) {
          for (Cookie cookie : request.getCookies()) {
              if ("token".equals(cookie.getName())) {
                  token = cookie.getValue();
              }
          }
      }

    if(SecurityContextHolder.getContext().getAuthentication() == null && token != null){
        // verifyTokenAndExtract prüft Signatur und Ablaufdatum und wirft bei ungültigem/abgelaufenem Token eine JwtException.
        // Dann läuft der Request einfach unauthentifiziert weiter (geschützte Pfade -> 401, /login etc. funktionieren weiterhin)
        try {
            Claims claims = jwtService.verifyTokenAndExtract(token);
            UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, new ArrayList<>());
            upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(upat);
        } catch (JwtException | IllegalArgumentException e) {
            SecurityContextHolder.clearContext();
        }
    }

    filter.doFilter(request, response);
  }
}