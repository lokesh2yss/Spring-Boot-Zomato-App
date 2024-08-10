package com.codingshuttle.app.zomatoApp.services.impl;

import com.codingshuttle.app.zomatoApp.entities.MenuItem;
import com.codingshuttle.app.zomatoApp.exceptions.ResourceNotFoundException;
import com.codingshuttle.app.zomatoApp.repositories.MenuItemRepository;
import com.codingshuttle.app.zomatoApp.services.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    @Override
    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public MenuItem updateMenuItem(Long menuItemID, MenuItem menuItem) {
        return menuItemRepository.save(menuItem);
    }

    @Override
    public boolean deleteMenuItem(Long menuItemId) {
        menuItemRepository.deleteById(menuItemId);
        return true;
    }

    @Override
    public MenuItem getMenuItemById(Long menuItemId) {
        return menuItemRepository.findById(menuItemId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Menu item not found with id:"+menuItemId));
    }
}
