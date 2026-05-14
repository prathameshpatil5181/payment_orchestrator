package com.orbyte.orchestrator.configuration;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.environment}")
    private String environment;

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {

        System.out.println(environment);

        if ("dev".equals(environment)) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000", "http://localhost:5173","http://localhost:8080")
                    .allowedMethods("*")
                    .allowedHeaders("*");
        }
    }
}
