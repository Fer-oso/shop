package com.ecommerce.shop.configurations.folder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class FolderConfig {

    @Value("${folder.path}")
    private String folderpath;

}
