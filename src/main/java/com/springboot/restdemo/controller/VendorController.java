package com.springboot.restdemo.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
// import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.restdemo.model.Vendor;
import com.springboot.restdemo.service.VendorService;

@RestController
@RequestMapping("/api/vendor")
public class VendorController 
{
    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);
    
    VendorService vendorService;

    public VendorController(VendorService vendorService) 
    {
        this.vendorService = vendorService;
    }

    @GetMapping("{vendorId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_USER')")
    public Vendor getVendorDetails(@PathVariable("vendorId") int vendorId) 
    {
        logger.error("This the error message of getVendorDetails");
        logger.info("User requested the vendor "+vendorId+" details");
        logger.debug("Inside getVendorDetails of Vendor Contoller");
        return vendorService.getVendor(vendorId);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_CREATOR')")
    public List<Vendor> getAllVendorDetails() 
    {
        logger.info("User requested the list of vendors");
        return vendorService.getAllVendors();
    }

    @GetMapping("/profile/{vendorId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_USER')")
    public ResponseEntity<?> getVendorProfileDetails(@PathVariable("vendorId") int vendorId) throws IOException
    {
        byte[] profile=vendorService.getVendorProfile(vendorId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(profile);
    }

    @GetMapping("/details/{vendorId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_USER')")
    public ModelAndView details(@PathVariable("vendorId") int vendorId) 
    {
        Vendor vendor = vendorService.getVendor(vendorId);
        
        ModelAndView modelAndView = new ModelAndView("details");
        modelAndView.addObject("vendor", vendor);
        modelAndView.addObject("items", vendor.getItems());
        return modelAndView;
    }

    @PostMapping
    // @Secured({"ROLE_CREATOR", "ROLE_ADMIN"})
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_CREATOR')")
    public String createVendorDetails(@RequestPart("data") Vendor vendor, @RequestPart("profile") MultipartFile profile/*@RequestParam("vendorId") String vendorId,*//*@RequestParam("vendorName") String vendorName,@RequestParam("vendorAddress") String vendorAddress,@RequestParam("vendorPhoneNumber") String vendorPhoneNumber*/) throws IOException
    {
        // CloudVendor cloudVendor = CloudVendor.builder()
        //                             .vendorName(vendorName)
        //                             .vendorAddress(vendorAddress)
        //                             .vendorPhoneNumber(vendorPhoneNumber)
        //                             .build();

        vendorService.createVendor(vendor, profile);
        return "Vendor Created";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_EDITOR')")
    public String updateVendorDetails(@RequestPart(name = "data",required = false) Vendor vendor, @RequestPart(name ="profile",required=false)  MultipartFile profile/*@RequestParam(name="vendorId",required = false) int vendorId,@RequestParam(name="vendorName",required = false) String vendorName,@RequestParam(name="vendorAddress") String vendorAddress,@RequestParam(name = "vendorPhoneNumber",required = false) String vendorPhoneNumber,*/) throws IOException
    {
        return vendorService.updateVendor(vendor, profile);
    }

    @DeleteMapping("{vendorId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_ADMIN')")
    public String deleteCloudVendorDetails(@PathVariable("vendorId") int vendorId) throws IOException 
    {
        vendorService.deleteVendor(vendorId);
        return "Vendor Deleted";
    }
}