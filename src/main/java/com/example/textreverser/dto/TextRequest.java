package com.example.textreverser.dto;

/**
 * Objet reçu par l'API REST (corps de la requête JSON).
 * Exemple : { "text": "bonjour" }
 */
public class TextRequest {

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
