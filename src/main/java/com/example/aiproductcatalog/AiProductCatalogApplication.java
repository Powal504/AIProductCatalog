package com.example.aiproductcatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class AiProductCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiProductCatalogApplication.class, args);
    }

}
