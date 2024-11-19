package com.ecommerce.shop.controllers.responsesModels;

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
public class ResponseErrosModel {

    private String code;
    private String status;
    private String message;
    private LocalDateTime timestamp;
}
