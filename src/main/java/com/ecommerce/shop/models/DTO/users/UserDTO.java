package com.ecommerce.shop.models.DTO.users;

import java.util.List;
import java.util.Set;

import com.ecommerce.shop.models.DTO.image.ImageDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private Set<RoleDTO> roles;
    private List<ImageDTO> profileImages;
}
