package com.springboot.restdemo.dto;

import java.util.Set;

import com.springboot.restdemo.model.Role;

import lombok.Data;

@Data
public class SignUpDto 
{
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
}
