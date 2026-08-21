package com.example.textreverser.controller;

import com.example.textreverser.dto.TextRequest;
import com.example.textreverser.dto.TextResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests unitaires du contrôleur (logique d'inversion et de mise en majuscules).
 */
class TextControllerTest {

    private final TextController controller = new TextController();

    @Test
    void reverseSimpleWord() {
        TextResponse response = controller.reverse(new TextRequest("bonjour"));
        assertEquals("ruojnob", response.getResult());
        assertEquals("bonjour", response.getOriginal());
    }

    @Test
    void reverseWithSpaces() {
        TextResponse response = controller.reverse(new TextRequest("hello world"));
        assertEquals("dlrow olleh", response.getResult());
    }

    @Test
    void reverseSpecialCharacters() {
        TextResponse response = controller.reverse(new TextRequest("café!"));
        assertEquals("!éfac", response.getResult());
    }

    @Test
    void reverseNumbers() {
        TextResponse response = controller.reverse(new TextRequest("12345"));
        assertEquals("54321", response.getResult());
    }

    @Test
    void uppercaseSimpleWord() {
        TextResponse response = controller.uppercase(new TextRequest("bonjour"));
        assertEquals("BONJOUR", response.getResult());
        assertEquals("bonjour", response.getOriginal());
    }

    @Test
    void uppercaseAlreadyUpper() {
        TextResponse response = controller.uppercase(new TextRequest("HELLO"));
        assertEquals("HELLO", response.getResult());
    }
}
