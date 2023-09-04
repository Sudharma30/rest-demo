package com.springboot.restdemo.service;

import java.util.List;

import com.springboot.restdemo.model.Item;

public interface ItemService 
{
    public String createItem(Item item);
    public String updateItem(Item item);
    public Item getItem(int itemId);
    public String deleteItem(int itemId);
    public List<Item> getAllItems();
}