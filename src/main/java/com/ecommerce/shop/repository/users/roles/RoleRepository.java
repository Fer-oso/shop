package com.ecommerce.shop.repository.users.roles;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.shop.models.entitys.user.Role;
import com.ecommerce.shop.models.entitys.user.enums.ROLE_NAME;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

 Optional<Role> findByRoleName(ROLE_NAME roleName);

 Set<Role> findAllByRoleNameIn(Set<ROLE_NAME> rolenames);
}
