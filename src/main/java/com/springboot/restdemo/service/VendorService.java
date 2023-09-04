package com.springboot.restdemo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.restdemo.model.Vendor;

public interface VendorService 
{
    public String createVendor(Vendor vendor,MultipartFile profile) throws IOException;
    public String updateVendor(Vendor vendor,MultipartFile profile) throws IOException;
    public String deleteVendor(int vendorId) throws IOException;
    public Vendor getVendor(int vendorId);
    public String getVendorPage(int vendorId)throws IOException;
    public List<Vendor> getAllVendors();
    public byte[] getVendorProfile(int vendorId) throws IOException;
}
