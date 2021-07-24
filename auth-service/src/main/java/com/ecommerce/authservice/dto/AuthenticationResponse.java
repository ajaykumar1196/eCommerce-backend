package com.ecommerce.authservice.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {

    private String token;
    private String username;
}