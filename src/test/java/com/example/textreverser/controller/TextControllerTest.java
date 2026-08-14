package com.example.textreverser.controller;

import com.example.textreverser.dto.TextRequest;
import com.example.textreverser.dto.TextResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests unitaires du contrôleur (logique d'inversion).
 */
class TextControllerTest {

    private final TextController controller = new TextController();

    @Test
    void reverseSimpleWord() {
        TextResponse response = controller.reverse(new TextRequest("bonjour"));
        assertEquals("ruojnob", response.getReversed());
        assertEquals("bonjour", response.getOriginal());
    }

    @Test
    void reverseEmptyString() {
        TextResponse response = controller.reverse(new TextRequest(""));
        assertEquals("", response.getReversed());
    }

    @Test
    void reverseNullText() {
        TextResponse response = controller.reverse(new TextRequest(null));
        assertEquals("", response.getReversed());
    }

    @Test
    void reverseNumbers() {
        TextResponse response = controller.reverse(new TextRequest("12345"));
        assertEquals("54321", response.getReversed());
    }
}
