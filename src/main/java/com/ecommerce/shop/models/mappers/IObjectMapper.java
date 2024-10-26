package com.ecommerce.shop.models.mappers;

public interface IObjectMapper<Entity, DTO> {

    Entity mapDTOToEntity(DTO dto);

    DTO mapEntityToDTO(Entity entity);
}
