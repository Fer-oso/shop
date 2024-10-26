package com.ecommerce.shop.controllers.products;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.shop.models.DTO.ProductDTO;
import com.ecommerce.shop.services.products.IProductService;

@RestController
@RequestMapping("/product")
public class ProductControler {

    IProductService productService;

    public ProductControler(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {

        try {

            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDTO));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long id) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(productService.update(productDTO, id));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findProductById(@PathVariable Long id) {

        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(productService.findById(id));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping()
    ResponseEntity<?> findAllProducts() {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(productService.findAll());

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No products found");
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable Long id) {

        try {

            productService.deleteById(id);

            return ResponseEntity.status(HttpStatus.OK).body("product with id " + id + "delete succesfully");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
