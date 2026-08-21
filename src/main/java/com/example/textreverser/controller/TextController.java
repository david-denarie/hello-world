package com.example.textreverser.controller;

import com.example.textreverser.dto.TextRequest;
import com.example.textreverser.dto.TextResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Contrôleur REST pour les transformations de texte.
 *
 * Endpoints :
 *   GET  /api/health    → healthcheck
 *   POST /api/reverse   → inverse le texte
 *   POST /api/uppercase → met le texte en majuscules
 *
 * Corps attendu (JSON) : { "text": "bonjour" }
 */
@RestController
@RequestMapping("/api")
public class TextController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    @PostMapping(
        value = "/reverse",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TextResponse reverse(@Valid @RequestBody TextRequest request) {
        String input = request.getText();
        String reversed = new StringBuilder(input).reverse().toString();

        return new TextResponse(input, reversed);
    }

    @PostMapping(
        value = "/uppercase",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TextResponse uppercase(@Valid @RequestBody TextRequest request) {
        String input = request.getText();
        String uppercased = input.toUpperCase();

        return new TextResponse(input, uppercased);
    }
}
