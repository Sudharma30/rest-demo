package com.springboot.restdemo.model;

import java.util.Set;

// import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
// import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
// import lombok.Getter;
import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.ToString;

@Data
// @Getter
// @Setter
@AllArgsConstructor
@NoArgsConstructor
// @ToString
@Builder
@Entity
@Table(name="vendor")
public class Vendor 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vendorId;
    private String vendorName;
    private String vendorAddress;
    private String vendorPhoneNumber;
    private String vendorProfilePath;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "license_id")
    private License license;

    // @OneToMany(targetEntity = Items.class,cascade = CascadeType.ALL, orphanRemoval = false) /*(mappedBy = "vendor")*/
    // @JoinTable(name = "vendor_items",
    //         joinColumns = {@JoinColumn(name = "vendorId")},
    //         inverseJoinColumns = {@JoinColumn(name = "itemId")})
    @ManyToMany
    @JoinTable(name = "vendor_items",
            joinColumns = @JoinColumn(name = "vendorId"),
            inverseJoinColumns = @JoinColumn(name = "itemId")
            )
    // @OrderBy("vendorId ASC")
    private Set<Item> items;
    
    // public Vendor(String vendorId, String vendorName, String vendorAddress, String vendorPhoneNumber, String vendorProfilePath) 
    // {
    //     this.vendorId = vendorId;
    //     this.vendorName = vendorName;
    //     this.vendorAddress = vendorAddress;
    //     this.vendorPhoneNumber = vendorPhoneNumber;
    //     this.vendorProfilePath = vendorProfilePath;
    // }

    // public String getVendorId()
    // {
    //     return vendorId;
    // }

    // public void setVendorId(String vendorId)
    // {
    //     this.vendorId = vendorId;
    // }

    // public String getVendorName()
    // {
    //     return vendorName;
    // }

    // public void setVendorName(String vendorName)
    // {
    //     this.vendorName = vendorName;
    // }

    // public String getVendorAddress()
    // {
    //     return vendorAddress;
    // }

    // public void setVendorAddress(String vendorAddress)
    // {
    //     this.vendorAddress = vendorAddress;
    // }

    // public String getVendorPhoneNumber()
    // {
    //     return vendorPhoneNumber;
    // }

    // public void setVendorPhoneNumber(String vendorPhoneNumber)
    // {
    //     this.vendorPhoneNumber = vendorPhoneNumber;
    // }

    // public String getVendorProfilePath() {
    //     return this.vendorProfilePath;
    // }

    // public void setVendorProfilePath(String vendorProfilePath) {
    //     this.vendorProfilePath = vendorProfilePath;
    // }

    // public Vendor()
    // {
    // }
}