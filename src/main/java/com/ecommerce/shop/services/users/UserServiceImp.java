package com.ecommerce.shop.services.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.shop.models.DTO.RoleDTO;
import com.ecommerce.shop.models.DTO.UserDTO;
import com.ecommerce.shop.models.mappers.UserMapper;
import com.ecommerce.shop.models.user.Role;
import com.ecommerce.shop.models.user.User;
import com.ecommerce.shop.models.user.enums.ROLE_NAME;
import com.ecommerce.shop.repository.users.UserRepository;
import com.ecommerce.shop.services.users.exceptions.DataBaseAccessException;
import com.ecommerce.shop.services.users.exceptions.DuplicateUsernameException;
import com.ecommerce.shop.services.users.exceptions.NoUsersFoundException;
import com.ecommerce.shop.services.users.exceptions.NullUserRequestException;
import com.ecommerce.shop.services.users.exceptions.RoleNotFoundException;
import com.ecommerce.shop.services.users.roles.IRoleService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserServiceImp implements IUserService, UserDetailsService {

    UserRepository userRepository;
    IRoleService roleService;

    PasswordEncoder passwordEncoder;

    UserMapper userMapper;

    public UserServiceImp(UserRepository userRepository,
            IRoleService roleService,
            PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {

        return Optional.of(userDTO)
                .map(dtoUser -> {

                    Set<Role> roleList = checkAndSetRoleList(dtoUser); // obtengo la lista de roles

                    dtoUser.setRoles(
                            roleList.stream().map(role -> new RoleDTO(role.getRoleName())).collect(Collectors.toSet()));
                    // seteo a el dto los roles obtenidos y los parseo a un RoleDTO en ese momento.
                    dtoUser.setPassword(passwordEncoder.encode(dtoUser.getPassword()));

                    User user = userMapper.mapDTOToEntity(dtoUser);

                    user.setRoles(roleList);

                    try {

                        return userMapper.mapEntityToDTO(userRepository.save(user));

                    } catch (Exception e) {

                        throw new DuplicateUsernameException(
                                "Username " + dtoUser.getUsername() + " is already in use.");
                    }
                })
                .orElseThrow(() -> new NullUserRequestException("User cant be null"));
    }

    private Set<Role> checkAndSetRoleList(UserDTO userDTO) {

        if (userDTO.getRoles().isEmpty()) {
            throw new RoleNotFoundException("Role/s not found");
        }

        Set<ROLE_NAME> rolesnameList = Set
                .copyOf(userDTO.getRoles().stream().map(rolename -> rolename.getRoleName()).toList());

        Set<Role> roleList = roleService.findAllByRoleNameIn(rolesnameList);

        return roleList;
    }

    @Override
    public UserDTO findById(Long id) {
        return userRepository.findById(id).map(user -> userMapper.mapEntityToDTO(user))
                .orElseThrow(() -> new EntityNotFoundException("NOT FOUND WITH THAT ID"));
    }

    @Override
    public UserDTO update(UserDTO userDTO, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<UserDTO> findAll() {

        try {
            
            var userList = userRepository.findAll();

            if(userList.isEmpty()) {
                
                throw new NoUsersFoundException("No users in database");
            }
            
            return userList.stream().map(user -> userMapper.mapEntityToDTO(user)).toList();

        } catch (DataAccessException e) {

            throw new DataBaseAccessException("Error accessing the database "+e.getMessage(), e);
        }
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