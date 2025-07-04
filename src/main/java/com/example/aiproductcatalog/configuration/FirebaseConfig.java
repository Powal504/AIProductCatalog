package com.example.aiproductcatalog.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Configuration
public class FirebaseConfig {

    private final ResourceLoader resourceLoader;

    public FirebaseConfig(
            ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        String firebaseCredentialJson = System.getenv(
                "GOOGLE_APPLICATION_CREDENTIALS_JSON");
        InputStream serviceAccount;
    
        if (firebaseCredentialJson != null) {
            serviceAccount = new ByteArrayInputStream(
                    firebaseCredentialJson.getBytes(StandardCharsets.UTF_8));
        } else {
            Resource resource = resourceLoader.getResource(
                    "classpath:aiproductcatalog-firebase.json");
            serviceAccount = resource.getInputStream();
        }
    
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
    
        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
    }
}
