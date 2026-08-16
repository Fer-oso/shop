package com.ecommerce.shop.services.images;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.models.DTO.image.ImageDTO;
import com.ecommerce.shop.models.entitys.image.Image;
import com.ecommerce.shop.models.mappers.ImageMapper;

import com.ecommerce.shop.repository.images.ImageRepository;
import com.ecommerce.shop.services.files.FileService;
import com.ecommerce.shop.services.images.cloudinary.CloudinaryService;
import com.ecommerce.shop.services.images.exceptions.ImageNotFoundException;
import com.ecommerce.shop.services.images.exceptions.ImageNotSelectedException;
import com.ecommerce.shop.services.images.exceptions.ImageUploadException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImageServiceImp implements IImageService {

    ImageRepository imageRepository;

    ImageMapper imageMapper;

    FileService fileService;

    CloudinaryService cloudinaryService;

    public ImageServiceImp(ImageRepository imageRepository, ImageMapper imageMapper, FileService fileService,
            CloudinaryService cloudinaryService) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
        this.fileService = fileService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("No image found with id: " + id));
    }

    @Override
    public String deleteById(Long id) {

        return imageRepository.findById(id).map(image -> {

            imageRepository.delete(image);

            return "Image: " + image.getFileName() + " deleted succesfuly with id: " + id;

        }).orElseThrow(() -> new ImageNotFoundException("Image not found with id: " + id));
    }

    @Override
    public List<ImageDTO> save(List<MultipartFile> filesImage) {

        /*
         * return buildImages(filesImage).stream()
         * .map(imageRepository::save)
         * .map(imageMapper::mapEntityToDTO)
         * .toList();
         * }
         */

        return buildImages(filesImage).stream().map(file -> imageRepository.save(file))
                .map(image -> imageMapper.mapEntityToDTO(image)).toList();
    }

    @Override
    public Image update(MultipartFile fileImage, Long imageId) {

        Image image = findById(imageId);

        try {

            image.setFileName(fileImage.getOriginalFilename());

            image.setFileType(fileImage.getContentType());

            image.setImage(fileImage.getBytes());

            return imageRepository.save(image);

        } catch (IOException e) {

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Image> saveImage(List<MultipartFile> filesImage) {

        return buildImages(filesImage);
    }

    private List<Image> buildImages(List<MultipartFile> filesImage) {

        fileService.checkIfFolderExists();

        if (filesImage == null || filesImage.isEmpty()) {
            return List.of();
        }

        return filesImage.stream()
                .filter(Objects::nonNull)
                .map(file -> {
                    if (file.getContentType() == null) {
                        throw new ImageNotSelectedException("File content type is null. Select one archive");
                    }
                    try {
                        Image image = Image.builder()
                                .fileName(fileService.uniqueFileName(file))
                                .fileType(file.getContentType())
                                .image(file.getBytes())
                                .downloadUrl(cloudinaryService.uploadImage(file))
                                .build();

                        fileService.saveInFolderImages(file);
                        return image;
                    } catch (IOException e) {
                        throw new ImageUploadException("Error al subir la imagen: " + file.getOriginalFilename(), e);
                    }
                })
                .toList();
    }

}
