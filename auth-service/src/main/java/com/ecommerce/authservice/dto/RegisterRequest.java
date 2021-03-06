package com.ecommerce.authservice.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;

}