package com.cni.plateformetesttechnique.security.jwt;


import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.util.Base64;

public class HS512KeyGenerator {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Generated HS512 Secret Key: " + encodedKey);
    }
}