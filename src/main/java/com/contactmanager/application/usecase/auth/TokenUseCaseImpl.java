package com.contactmanager.application.usecase.auth;

import com.contactmanager.infrastructure.ports.in.TokenUseCase;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;


@Component
public class TokenUseCaseImpl implements TokenUseCase {
    @Value("${security.secrets.key}")
    private String secretKey;

    @Override
    public String generateToken(Map<String, Object> claims) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claims(claims)
                .subject(claims.get("email").toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(15L, ChronoUnit.HOURS)))
                .signWith(getKey() )
                .compact();
    }

    @Override
    public Jws<Claims> parseJwt(String jwtString) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException {
        JwtParser jwtParser = Jwts.parser().verifyWith(getKey()).build();
        return jwtParser.parseSignedClaims(jwtString);
    }

    public SecretKey getKey(){
        String claveSecreta = secretKey;
        byte[] secretKeyBytes = claveSecreta.getBytes();
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }


}
