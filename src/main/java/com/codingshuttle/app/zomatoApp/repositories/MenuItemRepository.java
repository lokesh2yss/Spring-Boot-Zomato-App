package com.codingshuttle.app.zomatoApp.repositories;

import com.codingshuttle.app.zomatoApp.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
