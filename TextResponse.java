package com.example.textreverser.dto;

/**
 * Objet renvoyé par l'API REST (corps de la réponse JSON).
 * Exemple : { "original": "bonjour", "reversed": "ruojnob" }
 */
public class TextResponse {

    private String original;
    private String reversed;

    public TextResponse() {
    }

    public TextResponse(String original, String reversed) {
        this.original = original;
        this.reversed = reversed;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getReversed() {
        return reversed;
    }

    public void setReversed(String reversed) {
        this.reversed = reversed;
    }
}
