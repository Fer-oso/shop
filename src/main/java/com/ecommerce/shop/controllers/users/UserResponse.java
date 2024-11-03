package com.ecommerce.shop.controllers.users;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    
    private String message;
    private LocalDateTime timestamp;
    private String status;
    private String code;
}
