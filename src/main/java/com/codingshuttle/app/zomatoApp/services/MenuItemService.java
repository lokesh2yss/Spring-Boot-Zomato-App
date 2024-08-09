package com.codingshuttle.app.zomatoApp.services;

import com.codingshuttle.app.zomatoApp.entities.MenuItem;
import java.util.Optional;

public interface MenuItemService {
    MenuItem addMenuItem(MenuItem menuItem);
    MenuItem updateMenuItem(Long menuItemId, MenuItem menuItem);
    boolean deleteMenuItem(Long menuItemId);

    MenuItem getMenuItemById(Long menuItemId);
}
