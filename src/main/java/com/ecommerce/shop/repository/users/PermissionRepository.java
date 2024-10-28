package com.ecommerce.shop.repository.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shop.models.user.Permission;

public interface PermissionRepository extends JpaRepository<Permission,Long>{
    
    List<Permission> findByName(String name);
}
