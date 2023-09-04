package com.springboot.restdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.restdemo.model.Item;

public interface ItemRepository extends JpaRepository<Item,Integer>
{
    Item findByItemName(String itemName);
    // Items findByItemNameAndItemDescriptionAndItemPrice(String itemName, String itemDescription, String itemPrice);
}
