package com.example.textreverser.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Objet reçu par l'API REST (corps de la requête JSON).
 * Exemple : { "text": "bonjour" }
 */
public class TextRequest {

    @NotBlank(message = "Le champ 'text' ne doit pas être vide")
    @Size(max = 10000, message = "Le texte ne doit pas dépasser 10 000 caractères")
    private String text;

    public TextRequest() {
    }

    public TextRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
