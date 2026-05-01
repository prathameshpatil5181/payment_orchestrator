package com.orbyte.tokenizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TokenizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenizerApplication.class, args);
    }

}
