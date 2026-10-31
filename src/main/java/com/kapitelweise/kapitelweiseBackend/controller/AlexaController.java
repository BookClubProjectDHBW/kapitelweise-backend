package com.kapitelweise.kapitelweiseBackend.controller;

import com.kapitelweise.kapitelweiseBackend.modules.alexa.AlexaRO;
import com.kapitelweise.kapitelweiseBackend.service.AlexaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlexaController {
    private final AlexaService alexaService;

    public AlexaController(AlexaService alexaService) {
        this.alexaService = alexaService;
    }

    @GetMapping("/alexa")
    public String reachable() {
        return "Alexa-Endpunkt erreichbar";
    }

    @PostMapping("/alexa")
    public AlexaRO handleRequest(@RequestBody AlexaRO request) {
        return alexaService.handle(request);
    }
}
