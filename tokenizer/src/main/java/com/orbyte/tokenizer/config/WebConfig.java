package com.orbyte.tokenizer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${app.environment}")
    private String environment;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        System.out.println(environment);

        if ("dev".equals(environment)) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000", "http://localhost:5173","http://localhost:5003")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true); 

        }
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper;
    }

    @Bean
    public RestClient getRestClient(){
        return RestClient.create();
    }
}
