package com.springboot.restdemo.controller;

import java.util.HashSet;
import java.util.Set;

// import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.restdemo.dto.AuthRequest;
import com.springboot.restdemo.dto.SignUpDto;
import com.springboot.restdemo.model.Role;
import com.springboot.restdemo.model.User;
import com.springboot.restdemo.repository.RoleRepository;
import com.springboot.restdemo.repository.UserRepository;
import com.springboot.restdemo.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController 
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/signin")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest)
    {
        try
        {
            Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if(authentication.isAuthenticated())
            {
                return jwtService.generateToken(authRequest.getUsername());
            }
            else
            {
                throw new UsernameNotFoundException("Invalid User Request!");
            }
        }
        catch(AuthenticationException e)
        {
            return "Invalid username or password";
        }
    }

    // @PostMapping("/signin")
    // public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto)
    // {
    //     try
    //     {
    //         Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
    //                 loginDto.getUsername(), loginDto.getPassword()));

    //         SecurityContextHolder.getContext().setAuthentication(authentication);

    //         User user = userRepository.findByUsername(loginDto.getUsername()).orElseThrow(() -> 
    //         new UsernameNotFoundException("User not Exist"));

    //         String userEmail = user.getEmail();
    //         long expiry = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())+TimeUnit.MINUTES.toSeconds(2);
    //         String token = userEmail+"|"+expiry;

    //         HttpHeaders headers = new HttpHeaders();
    //         headers.add("Token",token);
    //         return new ResponseEntity<>("User signed-in successfully!\nToken: "+token, headers, HttpStatus.OK);
    //     }
    //     catch(AuthenticationException e)
    //     {
    //         return new ResponseEntity<>("Incorrect username or password",HttpStatus.UNAUTHORIZED);
    //     }
    // }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto)
    {
        if (signUpDto == null || signUpDto.getRoles() == null) 
        {
            return new ResponseEntity<>("Invalid request data", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUsername(signUpDto.getUsername()))
        {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpDto.getEmail()))
        {
            return new ResponseEntity<>( "Email address is already taken", HttpStatus.BAD_REQUEST );
        }
        
        // create user object
        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        for(Role role : signUpDto.getRoles())
        {
            Role existingRole = roleRepository.findByRole(role.getRole());
            if(existingRole != null)
            {
                roles.add(existingRole);
            }
            else
            {
                return new ResponseEntity<>("Role '"+role.getRole()+"'does not exist.",HttpStatus.BAD_REQUEST);
            }
        }
        user.setRoles(roles);

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}