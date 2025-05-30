package com.ecommerce.shop.controllers.users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.shop.controllers.responsesModels.ResponseSuccessModel;
import com.ecommerce.shop.models.DTO.users.UserDTO;
import com.ecommerce.shop.services.auth.AuthService;
import com.ecommerce.shop.services.users.IUserService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/shop/users")
public class UserController {

    IUserService userService;
    AuthService authService;

    public UserController(IUserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestPart("user") UserDTO userDTO,
            @RequestPart(name = "image", required = false) List<MultipartFile> images) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDTO, images));

    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> updateUser(@RequestPart("user") UserDTO userDTO,
            @RequestPart(name = "image", required = false) List<MultipartFile> images, @PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userDTO, id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ResponseSuccessModel> findAll() {

        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code("200")
                .response(userService.findAll())
                .timestamp(LocalDateTime.now())
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    ResponseEntity<ResponseSuccessModel> deleteById(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(ResponseSuccessModel.builder()
                .status("OK")
                .code("200")
                .response(userService.deleteById(id))
                .timestamp(LocalDateTime.now())
                .build());
    }
}
