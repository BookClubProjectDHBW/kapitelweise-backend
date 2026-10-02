package com.kapitelweise.kapitelweiseBackend.service;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
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
    if (token != null && authorizationHeader.startsWith("Bearer ")) {
        //7 weil im Header steht Bearer + Leerzeichen und danach fängts an - spannend
        token = authorizationHeader.substring(7);
        }

    if(SecurityContextHolder.getContext().getAuthentication() == null && token != null){
        
    Claims claims = jwtService.verifyTokenAndExtract(token);
    if(!jwtService.isTokenExpired(token)){
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, new ArrayList<>());
        upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(upat);
        
        }
    }

    filter.doFilter(request, response);
  }
}