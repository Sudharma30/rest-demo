package com.springboot.restdemo.service.impl;

// import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.restdemo.model.User;
import com.springboot.restdemo.repository.UserRepository;
// import com.springboot.restdemo.security.UserInfoDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    // public UserDetailsServiceImpl(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }
 
    @Override
    public UserDetails loadUserByUsername(String username) 
    {
        Optional<User> userDetails = userRepository.findByUsername(username);
        // .orElseThrow(() -> new UsernameNotFoundException("User not found with username: "+username));

        // return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new HashSet<>());
        return userDetails.map(UserDetailsImpl::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: "+username));
    }
}
