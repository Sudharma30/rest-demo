package com.springboot.restdemo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.restdemo.model.Item;
import com.springboot.restdemo.service.ItemService;

@RestController
@RequestMapping("/api/item")
public class ItemController 
{
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    // public ItemController(ItemService itemService)
    // {
    //     this.itemService = itemService;
    // }
    
    @GetMapping("{itemId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_VENDOR') or hasAuthority('ROLE_CREATOR')")
    public Item getItemDetails(@PathVariable("itemId") int itemId)
    {
        logger.info("User requested the item "+itemId+" details");
        logger.debug("Inside getItemDetails of Item Contoller");
        return itemService.getItem(itemId);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_VENDOR')")
    public List<Item> getAllItemDetails()
    {
        return itemService.getAllItems();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_VENDOR') or hasAuthority('ROLE_CREATOR')")
    public String createItemDetails(@RequestBody Item item)
    {
        return itemService.createItem(item);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') OR hasAuthority('ROLE_VENDOR') or hasAuthority('ROLE_EDITOR')")
    public String updateItemDetails(@RequestBody Item item)
    {
        return itemService.updateItem(item);
    }

    @DeleteMapping("{itemId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteItemDetails(@PathVariable("itemId") int itemId)
    {
        return itemService.deleteItem(itemId);
    }
}
