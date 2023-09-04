package com.springboot.restdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.springboot.restdemo.filter.EmailTokenAuthenticationFilter;
import com.springboot.restdemo.filter.JwtAuthenticationFilter;
import com.springboot.restdemo.service.impl.AccessDeniedHandlerImpl;
// import com.springboot.restdemo.repository.UserRepository;
// import com.springboot.restdemo.service.impl.UserDetailsServiceImpl;
import com.springboot.restdemo.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig 
{
    @Autowired
    private JwtAuthenticationFilter authFilter;

    @Autowired 
    private UserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetailsService userService = new UserDetailsServiceImpl();
        return userService;
    }

    // public SecurityConfig(UserDetailsService userDetailsService) 
    // {
    //     this.userDetailsService = userDetailsService;
    // }

    @Bean
    public static PasswordEncoder passwordEncoder() 
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception 
    {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() 
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler()
    {
        return new AccessDeniedHandlerImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                                        .requestMatchers("/api/auth/**").permitAll()
                                                        .requestMatchers("/api/**").authenticated()
                                                        .requestMatchers("/api/**").hasAuthority("ROLE_ADMIN"))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handling -> handling.accessDeniedHandler(accessDeniedHandler()));

                return http.build();
    }
}

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http, UserRepository userRepository) throws Exception 
    // {
    //     http.csrf(csrf -> csrf.disable())
    //             .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //             .authorizeHttpRequests((authorize) ->
    //             authorize
    //                     .requestMatchers("/api/auth/**").permitAll()
    //                     .requestMatchers("/api/**").authenticated()
    //                     .anyRequest().authenticated())
    //             .addFilterBefore(new EmailTokenAuthenticationFilter(userRepository),
    //                     UsernamePasswordAuthenticationFilter.class);
        /*
         * .httpBasic(httpBasic ->
         * httpBasic.realmName("My custom Realm"))
         */
        // return http.build();
    // }
