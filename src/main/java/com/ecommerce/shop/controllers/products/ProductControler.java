package com.ecommerce.shop.controllers.products;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
import com.ecommerce.shop.models.DTO.product.ProductDTO;
import com.ecommerce.shop.services.products.exceptions.ProductsNotFoundException;
import com.ecommerce.shop.services.products.productsStore.IProductService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping("${api.prefix}/products")
public class ProductControler {

    IProductService productService;

    public ProductControler(IProductService productService) {
        this.productService = productService;
    }

    /**
     * Crea un nuevo producto en el sistema junto con sus imágenes opcionales.
     *
     * <p>
     * Este endpoint acepta datos multipart/form-data, permitiendo el envío
     * simultáneo
     * de los datos del producto en formato JSON y archivos de imagen opcionales.
     * </p>
     *
     * <p>
     * <b>Acceso restringido:</b> Solo usuarios con rol {@code ADMIN} pueden
     * ejecutar
     * esta operación.
     * </p>
     *
     * @param productDTO objeto con los datos del producto a crear. Debe enviarse
     *                   como parte del formulario bajo la clave {@code "product"}.
     *                   No debe ser {@code null}.
     * @param images     lista de archivos de imagen asociados al producto. Se
     *                   envían
     *                   bajo la clave {@code "image"}. Este campo es opcional;
     *                   si no se proporciona, el producto se crea sin imágenes.
     *
     * @return {@link ResponseEntity} con estado HTTP {@code 201 CREATED} y un
     *         cuerpo
     *         {@link ResponseSuccessModel} que contiene:
     *         <ul>
     *         <li>{@code status} – {@code "CREATED"}</li>
     *         <li>{@code code} – {@code 201}</li>
     *         <li>{@code response} – el producto persistido devuelto por el
     *         servicio</li>
     *         <li>{@code timestamp} – fecha y hora de la operación</li>
     *         </ul>
     *
     * @throws ProductsNotFoundException       si algún recurso relacionado
     *                                         requerido para
     *                                         la creación no existe. Manejado
     *                                         globalmente
     *                                         por
     *                                         {@code GlobalProductExceptionHandler}
     *                                         →
     *                                         HTTP {@code 404}.
     * @throws AccessDeniedException           si el usuario autenticado no posee el
     *                                         rol
     *                                         {@code ADMIN}. Manejado globalmente
     *                                         por
     *                                         {@code GlobalProductExceptionHandler}
     *                                         →
     *                                         HTTP {@code 403}.
     * @throws MethodArgumentNotValidException si {@code productDTO} no pasa
     *                                         las validaciones. → HTTP 400
     * 
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ResponseSuccessModel> createProduct(@Valid @RequestPart("product") ProductDTO productDTO,
            @RequestPart(name = "image", required = false) List<MultipartFile> images) {

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseSuccessModel.builder()
                .status("CREATED")
                .code(201)
                .response(productService.save(productDTO, images))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseSuccessModel> findProductById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code(200)
                .response(productService.findById(id))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<ResponseSuccessModel> updateProduct(@Valid @RequestPart("product") ProductDTO productDTO,
            @RequestPart(name = "image", required = false) List<MultipartFile> images, @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code(201)
                .response(productService.update(productDTO, images, id))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseSuccessModel> deleteById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code(200)
                .response(productService.deleteById(id))
                .timestamp(LocalDateTime.now())
                .build());
    }

    /**
     * @throws ProductsNotFoundException → manejado por
     *                                   GlobalProductExceptionHandler
     *                                   (404)
     * @throws AccessDeniedException     → manejado por
     *                                   GlobalProductExceptionHandler
     *                                   (403)
     */
    @GetMapping()
    ResponseEntity<ResponseSuccessModel> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code(200)
                .response(productService.findAll())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping("/names")
    ResponseEntity<ResponseSuccessModel> findProductsByName(
            @RequestParam(required = false, defaultValue = "__ALL__") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code(200)
                .response(productService.findProductsByName(name))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping("/brand")
    ResponseEntity<ResponseSuccessModel> findProductsByBrand(
            @RequestParam(required = false, defaultValue = "__ALL__") String brand) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code(200)
                .response(productService.findProductsByBrand(brand))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping("/categories/{category}")
    ResponseEntity<?> findProductsByCategoryName(@PathVariable String category) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code(200)
                .response(productService.findProductsByCategoryName(category))
                .timestamp(LocalDateTime.now())
                .build());
    }

    @GetMapping("/brand/{brand}/name/{name}")
    ResponseEntity<?> findProductsByBrandAndName(@PathVariable String brand, @PathVariable String name) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.findProductsByBrandAndName(brand, name));
    }
}
