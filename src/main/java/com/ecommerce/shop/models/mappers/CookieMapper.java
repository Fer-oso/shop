package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.product.CookieDTO;
import com.ecommerce.shop.models.entitys.products.Cookie;

public class CookieMapper implements IObjectMapper<Cookie, CookieDTO> {

    ModelMapper modelMapper;

    public CookieMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Cookie mapDTOToEntity(CookieDTO cookieDTO) {

        return modelMapper.map(cookieDTO, Cookie.class);
    }

    @Override
    public CookieDTO mapEntityToDTO(Cookie cookie) {
        return modelMapper.map(cookie, CookieDTO.class);
    }

}
