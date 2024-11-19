package com.ecommerce.shop.controllers.responsesModels;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class ResponseSuccessModel {

    public String status;
    public String code;
    public Object response;
    public LocalDateTime timestamp;
}
