package com.ecommerce.shop.services.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.configurations.folder.FolderConfig;

@Service
public class FileService {

    FolderConfig folderConfig;

    Path FOLDER_IMAGES;

    public FileService(FolderConfig folderConfig) {
        this.folderConfig = folderConfig;
        this.FOLDER_IMAGES = Paths.get(folderConfig.getFolderpath());
        checkIfFolderExists();
    }

    public void checkIfFolderExists() {

        try {

            if (Files.exists(FOLDER_IMAGES)) { // Verifica si el directorio existe.

                System.out.println("El directorio ya existe: " + FOLDER_IMAGES.toAbsolutePath());

            } else {

                Files.createDirectories(FOLDER_IMAGES); // Crea el directorio si no existe.

                System.out.println("Directorio creado: " + FOLDER_IMAGES.toAbsolutePath());
            }

        } catch (IOException e) {

            System.err.println("Ocurrió un error al verificar o crear el directorio: " + e.getMessage());
        }
    }

    @SuppressWarnings("null")
    public String uniqueFileName(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {

            throw new RuntimeException(" file name contains invalid path sequence" + fileName);
        }

        return UUID.randomUUID().toString() + "_" + fileName;
    }

    public void saveInFolderImages(MultipartFile file) throws IOException {

        String uniqueFileName = uniqueFileName(file);

        Files.copy(file.getInputStream(), FOLDER_IMAGES.resolve(uniqueFileName), StandardCopyOption.REPLACE_EXISTING);
    }
}
