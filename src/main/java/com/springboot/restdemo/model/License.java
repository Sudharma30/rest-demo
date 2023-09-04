package com.springboot.restdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "license")
// @NoArgsConstructor
public class License 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int licenseId;
    private String licenseNumber;
    private String licenseIssueYear;
    @JsonIgnore
    @OneToOne(mappedBy = "license")
    private Vendor vendor;
}
