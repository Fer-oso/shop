package com.ecommerce.shop.services.users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.user.Role;
import com.ecommerce.shop.models.user.User;
import com.ecommerce.shop.models.user.enums.ROLE_NAME;
import com.ecommerce.shop.repository.users.RoleRepository;
import com.ecommerce.shop.repository.users.UserRepository;

@Service
public class UserServiceImp implements IUserService, UserDetailsService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User save(User user) {

        if (!user.getRoles().isEmpty()) {

            Set<ROLE_NAME> rolesnameList = Set
                    .copyOf(user.getRoles().stream().map(rolename -> rolename.getRoleName()).toList());

            Set<Role> roleList = new HashSet<>(roleRepository.findAllByRoleNameIn(rolesnameList));

            user.setRoles(roleList);
        }

        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User update(User t, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username" + username + "not found"));

        List<SimpleGrantedAuthority> listAuthorities = new ArrayList<>();

        user.getRoles().forEach(
                role -> listAuthorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName().name()))));

        user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> listAuthorities.add(new SimpleGrantedAuthority(permission.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
                listAuthorities);
    }

}
// Set<ROLE_NAME> rolesnameList = user.getRoles().stream().map(rolename ->
// rolename.getRoleName()).collect(Collectors.toSet());

// Set<ROLE_NAME> rolesnameList = user.getRoles().stream().forEach(roles ->
// rolesnameList.add(roles.getRoleName()));

// Set<Role> roleList = rolesnameList.stream().map(role ->
// roleRepository.findByRoleName(role).get()).collect(Collectors.toSet());

// Set<Role> roleList = rolesnameList.stream().forEach( rolename -> {
// roleList.add(roleRepository.findByRoleName(rolename).get()); });