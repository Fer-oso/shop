package com.ecommerce.shop.controllers.images;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.models.DTO.image.ImageDTO;
import com.ecommerce.shop.models.entitys.image.Image;
import com.ecommerce.shop.services.images.IImageService;
import com.ecommerce.shop.services.images.cloudinary.CloudinaryService;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImagesController {

    IImageService imageService;

    CloudinaryService cloudinaryService;

    public ImagesController(IImageService imageService, CloudinaryService cloudinaryService) {
        this.imageService = imageService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload/multiple")
    public ResponseEntity<?> saveImages(@RequestParam List<MultipartFile> files) {

        try {
            List<ImageDTO> imageDTOs = imageService.save(files);

            return ResponseEntity.status(HttpStatus.OK).body(imageDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam List<MultipartFile> file) {
        try {
            List<ImageDTO> imageUrl = imageService.save(file);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {

        Image image = imageService.findById(imageId);

        ByteArrayResource resource = new ByteArrayResource(
                image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> findById(@PathVariable Long imageId) throws SQLException {

        try {
            Image image = imageService.findById(imageId);

            ByteArrayResource resource = new ByteArrayResource(
                    image.getImage().getBytes(1, (int) image.getImage().length()));

            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(image.getFileType()))
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
