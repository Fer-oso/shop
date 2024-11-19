package com.ecommerce.shop.models.mappers;

import org.modelmapper.ModelMapper;

import com.ecommerce.shop.models.DTO.ImageDTO;
import com.ecommerce.shop.models.image.Image;

public class ImageMapper implements IObjectMapper<Image, ImageDTO> {

    ModelMapper modelMapper;

    public ImageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Image mapDTOToEntity(ImageDTO imageDTO) {
        return modelMapper.map(imageDTO, Image.class);
    }

    @Override
    public ImageDTO mapEntityToDTO(Image image) {
        return modelMapper.map(image, ImageDTO.class);
    }

    @Override
    public void updateEntityFromDTO(ImageDTO imageDTO, Image image) {
        modelMapper.map(imageDTO, image);
    }

}
