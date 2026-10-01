package com.kapitelweise.kapitelweiseBackend;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kapitelweise.kapitelweiseBackend.request.LoginRequest;
import com.kapitelweise.kapitelweiseBackend.service.JwtService;

@RestController
public class LoginController {

     private final AuthenticationManager authenticationManager;
     private final JwtService jwtService;

    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/hey")
    public String hey() {
        return "Hey there!";
    }


   @PostMapping("/login")
public String login(@RequestBody LoginRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));
    String token = jwtService.generateToken(request.username());
    return token;
}
}


