// package com.springboot.restdemo.security;

// import org.springframework.security.authentication.AbstractAuthenticationToken;

// public class EmailTokenAuthenticationToken extends AbstractAuthenticationToken
// {
//     private final String email;

//     public EmailTokenAuthenticationToken(String email) 
//     {
//         super(null);
//         this.email = email;
//         setAuthenticated(true);
//     }

//     @Override
//     public Object getCredentials()
//     {
//         return null;
//     }

//     @Override
//     public Object getPrincipal()
//     {
//         return email;
//     }
// }
