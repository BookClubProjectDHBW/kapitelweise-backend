package com.kapitelweise.kapitelweiseBackend.controller;


import com.kapitelweise.kapitelweiseBackend.response.MeResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/users/me")
    public MeResponse returnUsername(@AuthenticationPrincipal String username){
        System.out.println(username);
        return new MeResponse(username);
    }
}
