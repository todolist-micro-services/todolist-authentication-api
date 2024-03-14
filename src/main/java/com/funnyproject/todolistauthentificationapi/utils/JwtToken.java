package com.funnyproject.todolistauthentificationapi.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JwtToken {
    public static JwtTokenType createJwtToken(String secret, String name, String email, String subject, int nbrHour) {
        JwtTokenType jwtTokenType = new JwtTokenType();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expirationDateTime = currentDateTime.plusHours(nbrHour);
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
        String jwtToken = Jwts.builder()
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(hmacKey)
                .compact();

        jwtTokenType.setJwtValue(jwtToken);
        jwtTokenType.setExpirationDate(expirationDateTime);
        return jwtTokenType;
    }
}
