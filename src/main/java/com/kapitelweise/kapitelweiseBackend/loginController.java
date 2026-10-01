package com.kapitelweise.kapitelweiseBackend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class loginController {
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/hey")
    public String hey() {
        return "Hey there!";
    }
}

