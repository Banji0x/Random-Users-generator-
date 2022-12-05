package com.generator.randomusersgenerator.security;


import com.nimbusds.jose.jwk.RSAKey;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;


@SuppressWarnings("SpellCheckingInspection")
public class Jwks {
    public static RSAKey generateRSAKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicRSAKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateRSAKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicRSAKey)
                .privateKey(privateRSAKey)
                .keyID(UUID.randomUUID().toString()).build();
    }
}
