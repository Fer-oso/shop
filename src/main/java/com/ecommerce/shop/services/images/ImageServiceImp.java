package com.ecommerce.shop.services.images;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.models.DTO.ImageDTO;
import com.ecommerce.shop.models.image.Image;
import com.ecommerce.shop.models.mappers.ImageMapper;
import com.ecommerce.shop.models.mappers.ProductMapper;

import com.ecommerce.shop.repository.images.ImageRepository;
import com.ecommerce.shop.services.images.exceptions.ImageNotFoundException;
import com.ecommerce.shop.services.images.exceptions.ImageNotSelectedException;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImageServiceImp implements IImageService {

    ImageRepository imageRepository;

    ProductMapper productMapper;
    ImageMapper imageMapper;

    private static final String URL_DOWNLOAD = "/api/shop/images/image/download/";
    private static final Path FOLDER_IMAGES = Paths.get("src//main//resources//public//img");

    public ImageServiceImp(ImageRepository imageRepository,
            ImageMapper imageMapper,
            ProductMapper productMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
        this.productMapper = productMapper;
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

        if (filesImage == null || filesImage.isEmpty()) {
            return List.of();
        }

        return filesImage.stream().filter(file -> file != null).map(file -> {

            try {

                Image image = Image.builder()
                        .fileName(uniqueFileName(file))
                        .fileType(file.getContentType())
                        .image(new SerialBlob(file.getBytes()))
                        .build();

                Image savedImage = imageRepository.save(image);

                String downloadUrl = URL_DOWNLOAD + savedImage.getId();

                savedImage.setDownloadUrl(downloadUrl);

                imageRepository.save(savedImage);

                saveInFolderImages(file);

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

    @SuppressWarnings("null")
    @Override
    public List<Image> saveImage(List<MultipartFile> filesImage) {

        try {

            if (filesImage == null || filesImage.isEmpty())
            return List.of();

            filesImage.get(0).getContentType().equals(null);
           
        } catch (Exception e) {
            throw new ImageNotSelectedException("Select one archive");
        }

        return filesImage.stream().map(file -> {

            try {

                Image image = Image.builder()
                        .fileName(uniqueFileName(file))
                        .fileType(file.getContentType())
                        .image(new SerialBlob(file.getBytes()))
                        .build();

                Image savedImage = imageRepository.save(image);

                String downloadUrl = URL_DOWNLOAD + savedImage.getId();

                savedImage.setDownloadUrl(downloadUrl);

                saveInFolderImages(file);

                return imageRepository.save(savedImage);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }).toList();
    }

    @SuppressWarnings("null")
    private String uniqueFileName(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {
            throw new RuntimeException(" file name contains invalid path sequence" + fileName);
        }

        return UUID.randomUUID().toString() + "_" + fileName;
    }

    private void saveInFolderImages(MultipartFile file) throws IOException {
        String uniqueFileName = uniqueFileName(file);
        Files.copy(file.getInputStream(), FOLDER_IMAGES.resolve(uniqueFileName), StandardCopyOption.REPLACE_EXISTING);
    }
}
