package com.orbyte.tokenizer.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbyte.tokenizer.dto.BinLookupResponse;
import com.orbyte.tokenizer.dto.CardInfo;
import com.orbyte.tokenizer.dto.EncryptTokenResponse;
import com.orbyte.tokenizer.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Base64;

@Service
@Slf4j
public class OrbyteTokenService {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int KEY_SIZE_BYTES = 32;
    private static final int IV_SIZE_BYTES = 12;
    private static final int TAG_SIZE_BITS = 128;

    private final SecretKey secretKey;
    private final ObjectMapper mapper = new ObjectMapper();
    private final BinService binService;

    public OrbyteTokenService(@Value("${orbyte.secrect}") String base64Key, BinService binService) {

        log.info("Initializing OrbyteTokenService");

        try {

            byte[] keyBytes = Base64.getDecoder().decode(base64Key);

            log.debug("Decoded secret key length: {}", keyBytes.length);

            if (keyBytes.length != KEY_SIZE_BYTES) {
                log.error("Invalid AES key size. Expected {} but got {}",
                        KEY_SIZE_BYTES,
                        keyBytes.length);

                throw new IllegalStateException(
                        "AES-256 requires exactly 32 bytes. Got: " + keyBytes.length
                );
            }

            this.secretKey = new SecretKeySpec(keyBytes, "AES");
            this.binService = binService;
            Arrays.fill(keyBytes, (byte) 0);

            log.info("AES secret key initialized successfully");

        } catch (Exception ex) {

            log.error("Failed to initialize encryption service", ex);
            throw ex;
        }
    }

    public EncryptTokenResponse getEncryptedPan(CardInfo cardInfo) {

        log.info("Starting PAN encryption");

        try {

            sanitizePan(cardInfo);

            log.debug("Card details validated successfully");

            String jsonValue = mapper.writeValueAsString(cardInfo);

            log.debug("Card info converted to JSON");

            String token = encrypt(jsonValue);

            log.info("PAN encrypted successfully");

            BinLookupResponse binLookupResponse = binService.getBinDetails(cardInfo.getCardNumber().substring(0,6));

            EncryptTokenResponse response =
                    EncryptTokenResponse.builder()
                            .cardNumber(encodeCardNumber(cardInfo.getCardNumber()))
                            .expiryMonth(cardInfo.getExpiryMonth())
                            .expiryYear(cardInfo.getExpiryYear())
                            .token(token)
                            .binDetails(binLookupResponse)
                            .build();


            cardInfo.setCardNumber("");
            cardInfo.setCvv("");

            log.info("Returning encrypted token response");

            return response;

        } catch (InvalidCardDetailsExecption ex) {

            log.error("Invalid card details provided", ex);
            throw ex;

        }
        catch (BinException ex){
            throw ex;
        }
        catch (Exception ex) {

            log.error("Exception while creating token", ex);

            throw new TokenErrorResponseException(
                    "Error Creating token",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    private String encrypt(String pan)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {

        log.debug("Starting encryption process");

        byte[] iv = generateIV();

        Cipher cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(
                Cipher.ENCRYPT_MODE,
                secretKey,
                new GCMParameterSpec(TAG_SIZE_BITS, iv)
        );

        byte[] cipherTextWithTag =
                cipher.doFinal(pan.getBytes(StandardCharsets.UTF_8));

        byte[] payload =
                new byte[IV_SIZE_BYTES + cipherTextWithTag.length];

        System.arraycopy(iv, 0, payload, 0, IV_SIZE_BYTES);

        System.arraycopy(
                cipherTextWithTag,
                0,
                payload,
                IV_SIZE_BYTES,
                cipherTextWithTag.length
        );

        Arrays.fill(iv, (byte) 0);

        log.debug("Encryption completed successfully");

        return Base64.getEncoder().encodeToString(payload);
    }

    private CardInfo sanitizePan(CardInfo cardInfo) {

        log.debug("Validating card information");

        cardInfo.setCardNumber(
                cardInfo.getCardNumber().replaceAll("\\s+", "")
        );

        cardInfo.setCvv(
                cardInfo.getCvv().replaceAll("\\s+", "")
        );

        int yearValue = LocalDate.now().getYear() % 100;

        if (cardInfo.getCardNumber().matches(".*[a-zA-Z].*")
                || cardInfo.getCardNumber().length() > 19
                || cardInfo.getCardNumber().length() < 16) {

            log.warn("Invalid card number received");

            throw new InvalidCardDetailsExecption("Invalid Card Details");
        }

        else if (cardInfo.getCvv().matches(".*[a-zA-Z].*")
                || cardInfo.getCvv().length() > 4
                || cardInfo.getCvv().length() < 3) {

            log.warn("Invalid CVV received");

            throw new InvalidCardDetailsExecption("Invalid Card Details");
        }

        else if (cardInfo.getExpiryMonth() > 12
                || cardInfo.getExpiryMonth() <= 0) {

            log.warn("Invalid expiry month received");

            throw new InvalidCardDetailsExecption("Invalid expiry month");
        }

        else if (cardInfo.getExpiryYear() < yearValue) {

            log.warn("Invalid expiry year received");

            throw new InvalidCardDetailsExecption("Invalid expiry year");
        }

        log.debug("Card validation completed successfully");

        return cardInfo;
    }

    private byte[] generateIV() {

        log.debug("Generating IV");

        byte[] iv = new byte[IV_SIZE_BYTES];

        new SecureRandom().nextBytes(iv);

        return iv;
    }

    private String encodeCardNumber(String cardNumber) {

        log.debug("Masking card number");

        StringBuilder builder = new StringBuilder(cardNumber);

        builder.replace(7, cardNumber.length() - 4, "XXXXXXX");

        return builder.toString();
    }

    public CardInfo getDecryptedPan(String encryptedToken) {

        log.info("Starting PAN decryption");

        try {

            String decryptedPan = decrypt(encryptedToken);

            log.debug("PAN decrypted successfully");

            CardInfo cardInfo =
                    mapper.readValue(decryptedPan, CardInfo.class);

            log.info("Returning decrypted PAN response");

            return cardInfo;

        } catch (Exception ex) {

            log.error("Error decrypting PAN", ex);

            throw new PanDecryptionException(
                    "Error in PAN decryption"
            );
        }
    }

    private String decrypt(String encryptedPan) {

        log.debug("Starting decrypt operation");

        try {

            byte[] payload =
                    Base64.getDecoder().decode(encryptedPan);

            if (payload.length < IV_SIZE_BYTES + 16) {

                log.error("Invalid encrypted payload");

                throw new IllegalArgumentException(
                        "Payload too short"
                );
            }

            byte[] iv =
                    Arrays.copyOfRange(payload, 0, IV_SIZE_BYTES);

            byte[] cipherTextWithTag =
                    Arrays.copyOfRange(
                            payload,
                            IV_SIZE_BYTES,
                            payload.length
                    );

            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey,
                    new GCMParameterSpec(TAG_SIZE_BITS, iv)
            );

            Arrays.fill(iv, (byte) 0);

            byte[] plainText =
                    cipher.doFinal(cipherTextWithTag);

            String pan =
                    new String(plainText, StandardCharsets.UTF_8);

            Arrays.fill(plainText, (byte) 0);

            log.debug("Decrypt operation completed successfully");

            return pan;

        } catch (AEADBadTagException ex) {

            log.error("Tampered encrypted data detected", ex);

            throw new PanEncryptionException(
                    "Tampered data detected"
            );

        } catch (Exception ex) {

            log.error("Decryption failed", ex);

            throw new PanEncryptionException(
                    "Decryption failed"
            );
        }
    }


}