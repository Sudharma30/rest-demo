package com.springboot.restdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.restdemo.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>
{
    Role findByRole(String role);
}
