package com.ecommerce.shop.controllers.products;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.controllers.responsesModels.ResponseSuccessModel;
import com.ecommerce.shop.models.DTO.ProductDTO;
import com.ecommerce.shop.services.products.IProductService;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductControler {

    IProductService productService;

    public ProductControler(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResponseSuccessModel> createProduct(@RequestPart("product") ProductDTO productDTO,
            @RequestParam(name = "image", required = false) List<MultipartFile> images) {

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseSuccessModel.builder()
                .status("CREATED")
                .code("201")
                .response(productService.save(productDTO, images))
                .timestamp(LocalDateTime.now())
                .build());

    }

    @PutMapping("/{id}")
    ResponseEntity<ResponseSuccessModel> updateProduct(@RequestPart("product") ProductDTO productDTO,
            @RequestPart(name = "image", required = false) List<MultipartFile> images, @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code("200")
                .response(productService.update(productDTO, images, id))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessModel> findProductById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code("200")
                .response(productService.findById(id))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessModel> deleteById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code("200")
                .response(productService.deleteById(id))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping()
    ResponseEntity<?> findAll() {

        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code("200")
                .response(productService.findAll())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping("/names")
    ResponseEntity<ResponseSuccessModel> findProductsByName(
            @RequestParam(required = false, defaultValue = "__ALL__") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code("200")
                .response(productService.findProductsByName(name))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping("/brand")
    ResponseEntity<ResponseSuccessModel> findProductsByBrand(
            @RequestParam(required = false, defaultValue = "__ALL__") String brand) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code("200")
                .response(productService.findProductsByBrand(brand))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping("/categories/{category}")
    ResponseEntity<?> findProductsByCategoryName(@PathVariable String category) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findProductsByCategoryName(category));
    }

    @GetMapping("/brand/{brand}/name/{name}")
    ResponseEntity<?> findProductsByBrandAndName(@PathVariable String brand, @PathVariable String name) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.findProductsByBrandAndName(brand, name));
    }
}
