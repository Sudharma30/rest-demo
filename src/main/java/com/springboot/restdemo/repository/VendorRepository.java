package com.springboot.restdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.restdemo.model.Vendor;


public interface VendorRepository extends JpaRepository <Vendor, Integer>
{
    Optional<Vendor> findByVendorId(int vendorId);
}
