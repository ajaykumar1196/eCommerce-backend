package com.ecommerce.authservice.controller;

import com.auth0.jwk.*;
import com.ecommerce.authservice.security.JwtTokenProvider;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/jwk")
public class JwkSetController {

    private final JwtTokenProvider jwtTokenProvider;
    private final JWKSet jwkSet;

    public JwkSetController(JwtTokenProvider jwtTokenProvider, JWKSet jwkSet) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwkSet = jwkSet;
    }

    @GetMapping("/testingJWT")
    public String testingJWT(@RequestParam String token){
        jwtTokenProvider.validateToken(token);
        JwkProvider http = new UrlJwkProvider("http://localhost:8080/api/jwk");
        JwkProvider provider = new GuavaCachedJwkProvider(http);
        Jwk jwk = null;
        try {
            jwk = provider.get("jwt-kid");
        } catch (JwkException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("JWK KEY in testingJWT : " + jwk.getPublicKey().getFormat());
        } catch (InvalidPublicKeyException e) {
            e.printStackTrace();
        }

        return "Working";
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        return jwkSet.toJSONObject();
    }

}