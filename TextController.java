package com.example.textreverser.controller;

import com.example.textreverser.dto.TextRequest;
import com.example.textreverser.dto.TextResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*") // Autorise les appels depuis n'importe quelle origine (pratique en dev)
public class TextController {

    @PostMapping("/reverse")
    public TextResponse reverse(@RequestBody TextRequest request) {
        String input = (request != null && request.getText() != null) ? request.getText() : "";

        // Inversion en tenant compte des caractères Unicode (emojis, accents composés, etc.)
        String reversed = new StringBuilder(input).reverse().toString();

        return new TextResponse(input, reversed);
    }
}
