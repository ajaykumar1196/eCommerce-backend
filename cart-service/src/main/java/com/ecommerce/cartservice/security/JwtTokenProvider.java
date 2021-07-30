package com.ecommerce.cartservice.security;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwt.JWT;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;


@Service
@Slf4j
public class JwtTokenProvider {

    public Claims getAllClaimsFromToken(String token) {

        JwkProvider provider = new UrlJwkProvider("http://localhost:8080/");
        PublicKey publicKey = null;

        try {

            DecodedJWT jwt = JWT.decode(token);
            String keyId = jwt.getKeyId();
            Jwk jwk = provider.get(keyId);
            publicKey = jwk.getPublicKey();

        } catch (JWTDecodeException | JwkException exception) {
            log.error("JWT Decode Exception");
        }

        return Jwts.parserBuilder()
                .setSigningKey(publicKey).build()
                .parseClaimsJws(token).getBody();

    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}