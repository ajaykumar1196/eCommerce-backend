package com.ecommerce.authservice.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Role implements GrantedAuthority {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private long id;

        private String authority;
}
