package com.example.textreverser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application Spring Boot.
 * Lance un serveur web embarqué (Tomcat) sur le port 8080 par défaut.
 */
@SpringBootApplication
public class TextReverserApplication {

    public static void main(String[] args) {
        SpringApplication.run(TextReverserApplication.class, args);
    }
}
