package com.ecommerce.authservice.security;

import com.ecommerce.authservice.domain.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.stream.Collectors;


@Service
@Slf4j
public class JwtTokenProvider {

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;
    private final JwkConfig jwkConfig;

    public JwtTokenProvider(RSAPrivateKey privateKey, RSAPublicKey publicKey, JwkConfig jwkConfig) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.jwkConfig = jwkConfig;
    }

    public String generateToken(Authentication authentication) {

        long now = System.currentTimeMillis();

        User user = (User) authentication.getPrincipal();

        return Jwts.builder()
                .setHeaderParam("kid", jwkConfig.getKid())
                .setSubject(user.getUsername())
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .claim("authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .claim("userId", user.getId())
                .setExpiration(new Date(now + 60 * 60 * 1000))
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey).build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(publicKey).build()
                    .parseClaimsJws(authToken);
            return true;

        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}