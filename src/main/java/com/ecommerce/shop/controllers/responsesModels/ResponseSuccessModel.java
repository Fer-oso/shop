package com.ecommerce.shop.controllers.responsesModels;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class ResponseSuccessModel<T> {

    public String status;
    public int code;
    public T response;
    public LocalDateTime timestamp;
}
