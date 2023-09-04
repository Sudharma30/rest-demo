package com.springboot.restdemo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.restdemo.model.Item;
import com.springboot.restdemo.repository.ItemRepository;
import com.springboot.restdemo.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService
{
    @Autowired
    ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository)
    {
        this.itemRepository = itemRepository;
    }

    @Override
    public String createItem(Item item)
    {
        itemRepository.save(item);
        return "Item Created Successfully";
    } 

    @Override
    public String updateItem(Item item)
    {
        itemRepository.save(item);
        return ("Item Updated Successfully");
    }

    @Override
    public String deleteItem(int itemId)
    {
        itemRepository.deleteById(itemId);
        return("Item Deleted Successfully");
    }

    @Override
    public Item getItem(int itemId)
    {
        return itemRepository.findById(itemId).get();
    }

    @Override
    public List<Item> getAllItems()
    {
        return itemRepository.findAll();
    }
}
