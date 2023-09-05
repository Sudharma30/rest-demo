package com.springboot.restdemo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.restdemo.model.Item;
import com.springboot.restdemo.model.Vendor;
import com.springboot.restdemo.repository.ItemRepository;
import com.springboot.restdemo.repository.VendorRepository;
import com.springboot.restdemo.service.VendorService;

@Service
public class VendorServiceImpl implements VendorService
{
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    ItemRepository itemRepository;

    public final String folder="D:/Important/SpringBoot/rest-demo-image/src/main/resources/";
    public final String path="static/Images/";
    public final String htmlPath="/src/main/resources/";
    public static String path() throws IOException
    {
        final String path= new ClassPathResource("static/Images/").getFile().getAbsolutePath();
        return path;
    }

    public static String dbPath() throws IOException
    {
        final String path= new ClassPathResource("").getFile().getAbsolutePath();
        return path;
    }

    // public VendorServiceImpl(VendorRepository vendorRepository) {
    //     this.vendorRepository = vendorRepository;
    // }


    @Override
    public String createVendor(Vendor vendor, MultipartFile profile) throws IllegalStateException, IOException
    {
        String profilePath=path()+"/"+vendor.getVendorName()+vendor.getLicense().getLicenseNumber()+".png";
        // System.out.println(profilePath);
        String dbPath = path+vendor.getVendorName()+vendor.getLicense().getLicenseNumber()+".png";
        // String profilePath1 = folder+dbPath;
        profile.transferTo(new File(profilePath));
        // profile.transferTo(new File(profilePath1));
        vendor.setVendorProfilePath(dbPath);
        
        // License license= new License(); 
        // license.setLicenseNumber(vendor.getLicense().getLicenseNumber());
        // license.setLicenseIssueYear(vendor.getLicense().getLicenseIssueYear());        


        // Vendor newVendor= Vendor.builder()
        //                         .vendorName(vendor.getVendorName())
        //                         .vendorAddress(vendor.getVendorAddress())
        //                         .vendorPhoneNumber(vendor.getVendorPhoneNumber())
        //                         .vendorProfilePath(vendor.getVendorProfilePath())
        //                         .license(license)
        //                         .build();

        // Vendor savedVendor = vendorRepository.save(vendor);

        Set<Item> itemsToAdd = new HashSet<>();
        for (Item item : vendor.getItems()) 
        {
            Item existingItem = itemRepository.findByItemName(item.getItemName());
            if (existingItem == null) 
            {
                existingItem = itemRepository.save(item);
            }
            itemsToAdd.add(existingItem);
        }
        vendor.setItems(itemsToAdd);
        vendorRepository.save(vendor);
        return "Successfully Created";
    }

    @Override
    public String updateVendor(Vendor vendor, MultipartFile profile) throws IllegalStateException, IOException 
    {
        Optional<Vendor> existingVendor = vendorRepository.findByVendorId(vendor.getVendorId());
        if(existingVendor.isPresent())
        {
            String dbPath = existingVendor.get().getVendorProfilePath()/*path+vendor.getVendorName()+".png"*/;
            String profilePath=dbPath()+"/"+existingVendor.get().getVendorProfilePath();
            // System.out.println(profilePath);
            if(profile!=null && !profile.isEmpty())
            {
                try
                {
                    File existingProfile = new File(profilePath);
                    if (existingProfile.exists()) 
                    {
                        existingProfile.delete();
                    }
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
                profilePath=path()+"/"+vendor.getVendorName()+vendor.getLicense().getLicenseNumber()+".png";
                profile.transferTo(new File(profilePath));
            }
            dbPath=path+vendor.getVendorName()+vendor.getLicense().getLicenseNumber()+".png";
            vendor.setVendorProfilePath(dbPath);

            Set<Item> itemsToAdd = new HashSet<>();
            for (Item item : vendor.getItems()) 
            {
                Item existingItem = itemRepository.findByItemName(item.getItemName());
                if (existingItem == null) 
                {
                    existingItem = itemRepository.save(item);
                }
                itemsToAdd.add(existingItem);
            }
            vendor.setItems(itemsToAdd);
            
            vendorRepository.save(vendor);
            return "Successfully Updated";
        }
        return "Vendor Does Not Exist";
    }

    @Override
    public String deleteVendor(int vendorId) throws IOException
    {
        Optional<Vendor> vendor = vendorRepository.findByVendorId(vendorId);
        if(vendor.isPresent())
        {
            String profilePath=dbPath()+"/"+vendor.get().getVendorProfilePath();
            // System.out.println(profilePath);
            File profile = new File(profilePath);
            if (profile.exists()) 
            {
                profile.delete();
            }
            vendorRepository.deleteById(vendorId);
            return "Succesfully Deleted Vendor "+vendorId;
        }
        return "Vendor "+vendorId+" Not Found";
    }
    @Override
    public Vendor getVendor(int vendorId) 
    {
        return vendorRepository.findById(vendorId).get();
    }

    @Override
    public String getVendorPage(int vendorId) throws IOException
    {
        Vendor vendor = vendorRepository.findById(vendorId).get();
        String profilePath=htmlPath+vendor.getVendorProfilePath();
        String details= "<!DOCTYPE html>\n"+
        "<html>\n"+
        "<head>\n"+
        "<title>Vendor + vendor.getVendorId() + Details</title>\n"+
        "</head>\n"+
        "<body>\n"+
        "<h1>Vendor Details</h1>\n"+
        "<p>Vendor ID: " + vendor.getVendorId() +" </p>\n"+
        "<p>Vendor Name: " + vendor.getVendorName() + "</p>\n"+
        "<p>Vendor Address: " + vendor.getVendorAddress() + "</p>\n"+
        "<p>Vendor Phone Number: " + vendor.getVendorPhoneNumber() +" </p>\n"+
        "<img src=\"/target/classes/static/Images/C4.png\" alt=\"Profile\" width=\"500px\">\n"+
        "</body>\n"+
        "</html>";
        System.out.println(profilePath);
        return details;
    }

    @Override
    public List<Vendor> getAllVendors() 
    {
        return vendorRepository.findAll();
    }
    
    @Override
    public byte[] getVendorProfile(int vendorId) throws IOException
    {
        Optional<Vendor> vendor=vendorRepository.findByVendorId(vendorId);
        String profilePath=dbPath()+"/"+vendor.get().getVendorProfilePath();
        byte[] profile=Files.readAllBytes(new File(profilePath).toPath());
        return profile;
    }
}