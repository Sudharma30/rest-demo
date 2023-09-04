package com.springboot.restdemo.model;

// import java.util.Collection;
// import java.util.Collections;
// import java.util.HashSet;
import java.util.Set;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user", uniqueConstraints =
    @UniqueConstraint(columnNames = {"username","email"})
)
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    private String email;
    private String password;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "userId"),
                inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;

    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() 
    // {
    //     return Collections.emptyList();
    // }

    // @Override
    // public boolean isAccountNonExpired() 
    // {
    //     return true;
    // }

    // @Override
    // public boolean isAccountNonLocked() 
    // {
    //     return true;
    // }

    // @Override
    // public boolean isCredentialsNonExpired() 
    // {
    //     return true;
    // }

    // @Override
    // public boolean isEnabled() 
    // {
    //     return true;
    // }
}