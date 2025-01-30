package com.ecommerce.shop.services.images;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.models.DTO.image.ImageDTO;
import com.ecommerce.shop.models.entitys.image.Image;

public interface IImageService {

    Image findById(Long id);

    String deleteById(Long id);

    List<ImageDTO> save(List<MultipartFile> fileImage);

    Image update(MultipartFile fileImage, Long imageId);

    List<Image> saveImage(List<MultipartFile> fileImage);

}
