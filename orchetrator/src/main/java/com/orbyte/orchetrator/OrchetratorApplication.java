package com.orbyte.orchetrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OrchetratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrchetratorApplication.class, args);
    }

}
