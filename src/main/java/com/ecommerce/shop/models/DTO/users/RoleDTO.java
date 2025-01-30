package com.ecommerce.shop.models.DTO.users;

import com.ecommerce.shop.models.entitys.user.enums.ROLE_NAME;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class RoleDTO {

    @Enumerated(EnumType.STRING)
    private ROLE_NAME roleName;
}
