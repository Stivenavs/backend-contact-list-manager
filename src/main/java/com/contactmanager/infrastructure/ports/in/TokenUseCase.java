package com.contactmanager.infrastructure.ports.in;

import io.jsonwebtoken.*;

import java.util.Map;

public interface TokenUseCase {
    String generateToken(Map<String, Object> claims);

    Jws<Claims> parseJwt(String jwtString) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException;
}
