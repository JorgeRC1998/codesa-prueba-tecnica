package com.pruebaTecnica.backendPrueba.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
 
@Configuration
@EnableWebMvc
public class Webconfig implements WebMvcConfigurer {
    
    // Quedaría mejor viniendo de variable de entorno
    private String origenapp = "http://localhost:4200";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
            .allowedOrigins("http://localhost:4200", "http://localhost:4000", "http://localhost:4100")
            .allowedHeaders("authorization", "Content-type")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowCredentials(true);
    }
 
}