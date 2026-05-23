package com.orbyte.tokenizer.services;

import com.orbyte.tokenizer.dto.KeyGenerationResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeyGenerationService {
    /**
     * Run once to generate your 256-bit key.
     * Store the output in a secret manager (AWS Secrets Manager, Vault, etc.)
     */

    @Value("${encryption.aes.key-algorithm}")
    private String encryptionAlgorithm;

    public KeyGenerationResponse generateSecureKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(encryptionAlgorithm);
            keyGen.init(256, new SecureRandom());
            SecretKey key = keyGen.generateKey();
            String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());
            KeyGenerationResponse keyResponse = new KeyGenerationResponse(base64Key);
            log.info("Key generated");
            return keyResponse;
        } catch (NoSuchAlgorithmException ex) {
            log.error("Exception in key generation {}", ex.getMessage());
            return  new KeyGenerationResponse(null);
        }
    }
}
