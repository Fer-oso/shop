package com.ecommerce.shop.services.images.cloudinary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    // Método para subir UNA imagen
    public String uploadImage(MultipartFile file) throws IOException {
        Map<?, ?> uploadResult = cloudinary.uploader().unsignedUpload(file.getBytes(), "tyg-products",
                ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }

    public List<String> uploadImages(List<MultipartFile> files) throws IOException {

        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            Map<?, ?> uploadResult = cloudinary.uploader().unsignedUpload(file.getBytes(), "tyg-products",
                    ObjectUtils.emptyMap());
            imageUrls.add(uploadResult.get("url").toString()); // Agrega la URL a la lista
        }

        return imageUrls;
    }

}
