package com.example.textreverser.dto;

/**
 * Objet renvoyé par les API REST de transformation de texte.
 *
 * Exemple : { "original": "bonjour", "result": "BONJOUR" }
 */
public class TextResponse {

    private String original;
    private String result;

    public TextResponse() {
    }

    public TextResponse(String original, String result) {
        this.original = original;
        this.result = result;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
