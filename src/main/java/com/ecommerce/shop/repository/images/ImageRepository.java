package com.ecommerce.shop.repository.images;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shop.models.image.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}