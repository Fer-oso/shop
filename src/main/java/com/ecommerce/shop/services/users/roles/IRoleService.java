package com.ecommerce.shop.services.users.roles;

import java.util.Set;

import com.ecommerce.shop.models.DTO.users.RoleDTO;
import com.ecommerce.shop.models.entitys.user.Role;
import com.ecommerce.shop.models.entitys.user.enums.ROLE_NAME;
import com.ecommerce.shop.services.interfaces.ICrudService;

public interface IRoleService extends ICrudService<RoleDTO,Long>{
    
    Set<Role> findAllByRoleNameIn(Set<ROLE_NAME> rolenames);
}
