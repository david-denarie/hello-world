package com.example.textreverser.controller;

import com.example.textreverser.dto.TextRequest;
import com.example.textreverser.dto.TextResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contrôleur REST qui reçoit un texte et le retourne inversé.
 *
 * Endpoint : POST /api/reverse
 * Corps attendu (JSON) : { "text": "bonjour" }
 * Réponse (JSON)      : { "original": "bonjour", "reversed": "ruojnob" }
 */
@RestController
@RequestMapping("/api")
public class TextController {

    @PostMapping(
        value = "/reverse",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public TextResponse reverse(@Valid @RequestBody TextRequest request) {
        String input = request.getText();

        // Inversion en tenant compte des caractères Unicode (emojis, accents composés, etc.)
        String reversed = new StringBuilder(input).reverse().toString();

        return new TextResponse(input, reversed);
    }
}
