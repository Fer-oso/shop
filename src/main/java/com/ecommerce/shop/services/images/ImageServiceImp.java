package com.ecommerce.shop.services.images;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.models.DTO.image.ImageDTO;
import com.ecommerce.shop.models.entitys.image.Image;
import com.ecommerce.shop.models.mappers.ImageMapper;
import com.ecommerce.shop.models.mappers.ProductMapper;

import com.ecommerce.shop.repository.images.ImageRepository;
import com.ecommerce.shop.services.files.FileService;
import com.ecommerce.shop.services.images.exceptions.ImageNotFoundException;
import com.ecommerce.shop.services.images.exceptions.ImageNotSelectedException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImageServiceImp implements IImageService {

    ImageRepository imageRepository;

    ProductMapper productMapper;
    ImageMapper imageMapper;

    FileService fileService;

    private static final String URL_DOWNLOAD = "/api/shop/images/image/download/";

    public ImageServiceImp(ImageRepository imageRepository,
            ImageMapper imageMapper,
            ProductMapper productMapper, FileService fileService) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
        this.productMapper = productMapper;
        this.fileService = fileService;
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

        fileService.checkIfFolderExists();

        if (filesImage == null || filesImage.isEmpty()) {

            return List.of();
        }

        return filesImage.stream().filter(file -> file != null).map(file -> {

            try {

                Image image = Image.builder()
                        .fileName(fileService.uniqueFileName(file))
                        .fileType(file.getContentType())
                        .image(new SerialBlob(file.getBytes()))
                        .build();

                Image savedImage = imageRepository.save(image);

                String downloadUrl = URL_DOWNLOAD + savedImage.getId();

                savedImage.setDownloadUrl(downloadUrl);

                imageRepository.save(savedImage);

                fileService.saveInFolderImages(file);

                return imageMapper.mapEntityToDTO(savedImage);

            } catch (IOException | SQLException e) {

                throw new RuntimeException(e.getMessage());
            }
        }).toList();
    }

    @Override
    public Image update(MultipartFile fileImage, Long imageId) {

        Image image = findById(imageId);

        try {

            image.setFileName(fileImage.getOriginalFilename());

            image.setFileType(fileImage.getContentType());

            image.setImage(new SerialBlob(fileImage.getBytes()));

            return imageRepository.save(image);

        } catch (SQLException | IOException e) {

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Image> saveImage(List<MultipartFile> filesImage) {

        fileService.checkIfFolderExists();

        if (filesImage == null || filesImage.isEmpty())
            return List.of();

        return filesImage.stream().map(file -> {

            try {

                if (file.getContentType() == null) {
                    throw new ImageNotSelectedException("File content type is null. Select one archive");
                }

                Image image = Image.builder()
                        .fileName(fileService.uniqueFileName(file))
                        .fileType(file.getContentType())
                        .image(new SerialBlob(file.getBytes()))
                        .build();

                Image savedImage = imageRepository.save(image);

                String downloadUrl = URL_DOWNLOAD + savedImage.getId();

                savedImage.setDownloadUrl(downloadUrl);

                fileService.saveInFolderImages(file);

                return imageRepository.save(savedImage);

            } catch (IOException | SQLException e) {

                throw new RuntimeException(e.getMessage());
            }
        }).toList();
    }

}
