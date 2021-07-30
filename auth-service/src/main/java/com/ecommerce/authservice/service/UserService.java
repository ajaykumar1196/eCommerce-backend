package com.ecommerce.authservice.service;

import com.ecommerce.authservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username);
        if (userDetails == null){
            throw new UsernameNotFoundException("User not exist with username '" + username + "'");
        }
        return userDetails;
    }

    public Boolean checkUserExists(String userName){
        return userRepository.existsByUsername(userName);
    }
}
