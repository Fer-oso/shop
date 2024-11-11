package com.ecommerce.shop.controllers.response;

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
public class ResponseModel {
    
    private String code;
    private String status;
    private String message;
    private LocalDateTime timestamp;
}
