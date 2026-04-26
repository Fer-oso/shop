package com.ecommerce.shop.models.DTO.users;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class UserLoginResponseDTO {

    private Long id;

    private String username;

    private String token;

    @JsonIgnore
    private String refreshToken;

    private Set<RoleDTO> roles;
}
