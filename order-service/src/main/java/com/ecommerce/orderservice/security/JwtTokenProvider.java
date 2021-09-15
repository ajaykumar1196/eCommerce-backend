package com.ecommerce.orderservice.security;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;


@Service
@Slf4j
public class JwtTokenProvider {

    public Map<String, Claim> getAllClaimsFromToken(String token) {

        JwkProvider provider = new UrlJwkProvider("http://localhost:8080/");

        Map<String, Claim> claims;

        try {

            DecodedJWT jwt = JWT.decode(token);
            String keyId = jwt.getKeyId();

            Jwk jwk = provider.get(keyId);

            PublicKey publicKey = jwk.getPublicKey();
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm).build();

            verifier.verify(token);

            claims = jwt.getClaims();

        } catch (JWTVerificationException | JwkException exception) {
            log.error("JWT Verification Exception");
            throw new IllegalStateException(String.format("Token %s cannot be trusted ", token));
        }

        return claims;

    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}