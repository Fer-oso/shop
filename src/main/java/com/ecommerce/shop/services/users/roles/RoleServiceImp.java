package com.ecommerce.shop.services.users.roles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.RoleDTO;
import com.ecommerce.shop.models.mappers.RoleMapper;
import com.ecommerce.shop.models.user.Role;
import com.ecommerce.shop.models.user.enums.ROLE_NAME;
import com.ecommerce.shop.repository.users.roles.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImp implements IRoleService{

    RoleRepository roleRepository;
    RoleMapper roleMapper;

    public RoleServiceImp(RoleRepository roleRepository,RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDTO save(RoleDTO t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public RoleDTO findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public RoleDTO update(RoleDTO t, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public String deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public List<RoleDTO> findAll() {
        return null;
    }
    
    @Override
    public Set<Role> findAllByRoleNameIn(Set<ROLE_NAME> rolenames) {
        
        return roleRepository.findAllByRoleNameIn(rolenames); 
    }
}
