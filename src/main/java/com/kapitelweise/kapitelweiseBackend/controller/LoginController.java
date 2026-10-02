package com.kapitelweise.kapitelweiseBackend.controller;

import com.kapitelweise.kapitelweiseBackend.request.RegistrationRequest;
import com.kapitelweise.kapitelweiseBackend.service.RegistrationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kapitelweise.kapitelweiseBackend.request.LoginRequest;
import com.kapitelweise.kapitelweiseBackend.service.JwtService;
import java.util.logging.Logger;

@RestController
public class LoginController {

     private final AuthenticationManager authenticationManager;
     private final JwtService jwtService;
     private final RegistrationService registrationService;

    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService, RegistrationService registrationService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.registrationService = registrationService;
    }

    Logger logger
            = Logger.getLogger(
            LoginController.class.getName());
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/hey")
    public String hey() {
        return "Hey there!";
    }



    @PostMapping("/register")
    public void register(@RequestBody RegistrationRequest request){
        registrationService.register(request);
    }
//so wie ich es verstehe, das ist nur die Logik um den Token zu erzeugen! Basic Auth ist immer noch möglich!!!
   @PostMapping("/login")
public String login(@RequestBody LoginRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));
    String token = jwtService.generateToken(request.username());
    return token;
}
}


