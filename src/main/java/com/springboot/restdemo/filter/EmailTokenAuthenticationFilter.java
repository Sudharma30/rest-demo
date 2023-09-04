// package com.springboot.restdemo.filter;

// import java.io.IOException;
// import java.util.concurrent.TimeUnit;

// // import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// // import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.web.filter.OncePerRequestFilter;

// import com.springboot.restdemo.repository.UserRepository;
// // import com.springbootdemo.service.impl.UserDetailsServiceImpl;
// import com.springboot.restdemo.security.EmailTokenAuthenticationToken;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// public class EmailTokenAuthenticationFilter extends OncePerRequestFilter
// {
//     private UserRepository userRepository; // Assuming you have a UserRepository

//     public EmailTokenAuthenticationFilter(UserRepository userRepository) 
//     {
//         this.userRepository = userRepository;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain) throws ServletException,IOException
//     {
//         String token = request.getHeader("Authorization");
//         String path = request.getRequestURI();

//         System.out.println(token);

//         if("/api/auth/signin".equals(path)||"/api/auth/signup".equals(path))
//         {
//             filterChain.doFilter(request, response);
//             return;
//         }

//         if(isValidToken(token))
//         {
//             Authentication authentication = new EmailTokenAuthenticationToken(token);
//             SecurityContextHolder.getContext().setAuthentication(authentication);
//         }
//         // else if(token==null)
//         // {
//         //     SecurityContextHolder.getContext().setAuthentication(null);
//         // }
//         else
//         {
//             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//             response.getWriter().write("User not signed in");
//             return;
//         }
//         filterChain.doFilter(request, response);
//     }

//     private boolean isValidToken(String token) 
//     {
//         String[] tokenParts = token.split("\\|");
//         if (tokenParts.length != 2) 
//         {
//             return false;
//         }

//         String userEmail = tokenParts[0];
//         long expirationMillis = Long.parseLong(tokenParts[1]);
//         long currentMillis = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

//         // Check if the token has expired
//         if (currentMillis > expirationMillis) 
//         {
//             return false;
//         }

//         return userRepository.existsByEmail(userEmail);
//     }
// }