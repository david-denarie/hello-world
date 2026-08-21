package com.example.textreverser.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

/**
 * Configure Spring Boot pour servir le frontend Angular en mode SPA.
 *
 * - Les fichiers statiques (JS, CSS, assets) sont servis depuis classpath:/static/
 * - Toute route qui ne correspond pas à un fichier ni à /api/** est redirigée vers index.html
 *   (pour que le routeur Angular gère la navigation côté client)
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .resourceChain(true)
            .addResolver(new PathResourceResolver() {
                @Override
                protected Resource getResource(String resourcePath, Resource location) throws IOException {
                    Resource requested = location.createRelative(resourcePath);

                    // Si le fichier existe, le servir directement
                    if (requested.exists() && requested.isReadable()) {
                        return requested;
                    }

                    // Sinon, servir index.html (SPA fallback)
                    // Sauf pour les appels API (gérés par le contrôleur)
                    if (resourcePath.startsWith("api/")) {
                        return null;
                    }

                    return new ClassPathResource("/static/index.html");
                }
            });
    }
}
