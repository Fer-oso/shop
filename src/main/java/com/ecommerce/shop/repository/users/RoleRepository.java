package com.ecommerce.shop.repository.users;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.shop.models.user.Role;
import com.ecommerce.shop.models.user.enums.ROLE_NAME;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{

 Optional<Role> findByRoleName(ROLE_NAME roleName);

 List<Role> findAllByRoleNameIn(Set<ROLE_NAME> rolenames);
}
