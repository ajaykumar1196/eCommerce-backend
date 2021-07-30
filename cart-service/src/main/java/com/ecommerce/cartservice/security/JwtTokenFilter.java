package com.ecommerce.cartservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        String jwt = jwtTokenProvider.resolveToken(httpServletRequest);

        try {

            if(StringUtils.hasText(jwt)){

                Claims claims = jwtTokenProvider.getAllClaimsFromToken(jwt);

                Long userId = Long.valueOf((Integer) claims.get("userId"));

                List<String> authorities = (List<String>) claims.get("authorities");

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null,
                                authorities
                                        .stream()
                                        .map(SimpleGrantedAuthority::new)
                                        .collect(Collectors.toList()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        } catch (JwtException e) {
            SecurityContextHolder.clearContext();
            throw new IllegalStateException(String.format("Token %s cannot be trusted", jwt));
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
