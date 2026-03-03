package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.user.entity.User;
import com.example.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    public UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
