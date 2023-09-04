package com.springboot.restdemo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public interface JwtService 
{
    public String extractUsername(String token);
    public String generateToken(String username);
    public Boolean validateToken(String token, UserDetails userDetails);
}
